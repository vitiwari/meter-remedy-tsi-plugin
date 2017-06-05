/**
 *
 * @author Santosh Patil
 * @Date 22-05-2017
 */
package com.bmc.truesight.meter.plugin.remedy;

import com.bmc.truesight.meter.plugin.remedy.util.ConfigParser;
import com.bmc.truesight.meter.plugin.remedy.util.ConfigValidator;
import com.bmc.truesight.meter.plugin.remedy.util.Constants;
import com.bmc.truesight.meter.plugin.remedy.util.RequestType;
import com.bmc.truesight.meter.plugin.remedy.util.Util;
import com.bmc.truesight.remedy.exception.ParsingException;
import com.bmc.truesight.remedy.exception.ValidationException;
import com.boundary.plugin.sdk.CollectorDispatcher;
import com.boundary.plugin.sdk.Event;
import com.boundary.plugin.sdk.EventSink;
import com.boundary.plugin.sdk.EventSinkAPI;
import com.boundary.plugin.sdk.EventSinkStandardOutput;
import com.boundary.plugin.sdk.MeasurementSink;
import com.boundary.plugin.sdk.Plugin;
import com.boundary.plugin.sdk.PluginRunner;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemedyPlugin implements Plugin<RemedyPluginConfiguration> {

    RemedyPluginConfiguration configuration;
    CollectorDispatcher dispatcher;
    EventSink eventOutput;
    EventSinkAPI eventSinkAPI;
    private static final Logger LOG = LoggerFactory
            .getLogger(RemedyPlugin.class);

    @Override
    public void setConfiguration(RemedyPluginConfiguration configuration) {
        this.configuration = configuration;
        this.eventOutput = new EventSinkStandardOutput();
        this.eventSinkAPI = new EventSinkAPI();
    }

    @Override
    public void setEventOutput(final EventSink output) {
        this.eventOutput = output;
    }

    @Override
    public void loadConfiguration() {
        Gson gson = new Gson();
        try {
            RemedyPluginConfiguration pluginConfiguration = gson.fromJson(new FileReader("param.json"), RemedyPluginConfiguration.class);
            setConfiguration(pluginConfiguration);
        } catch (JsonParseException e) {
            eventSinkAPI.emit(Util.event(Constants.REMEDY_PLUGIN_TITLE_MSG, e.getMessage(), "", Event.EventSeverity.ERROR.toString()));
            LOG.error("Exception occured while getting the param.json data", e);
        } catch (IOException e) {
            eventSinkAPI.emit(Util.event(Constants.REMEDY_PLUGIN_TITLE_MSG, e.getMessage(), "", Event.EventSeverity.ERROR.toString()));
            LOG.error("IOException occured while getting the param.json data", e);
        }
    }

    @Override
    public void setDispatcher(CollectorDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        ArrayList<RemedyPluginConfigurationItem> items = configuration.getItems();
        items.forEach((config) -> {
            boolean isValidJson = true;
            //PARSING THE JSON STRING
            ConfigParser configParser = new ConfigParser(Util.getFieldValues(config.getFileds()));
            try {
                configParser.readParseConfigJson(Util.getFieldValues(config.getFileds()));
            } catch (ParsingException ex) {
                eventSinkAPI.emit(Util.event(Constants.REMEDY_PLUGIN_TITLE_MSG, ex.getMessage(), config.getHostName(), Event.EventSeverity.ERROR.toString()));
                isValidJson = false;
            }
            //VALIDATION OF THE JSON STRING
            ConfigValidator configValidator = new ConfigValidator();
            try {
                configValidator.validate(configParser);
            } catch (ValidationException ex) {
                eventSinkAPI.emit(Util.event(Constants.REMEDY_PLUGIN_TITLE_MSG, ex.getMessage(), config.getHostName(), Event.EventSeverity.ERROR.toString()));
                isValidJson = false;
            }
            if (isValidJson) {
                if (config.getRequestType().equalsIgnoreCase(RequestType.IM.getValues())) {
                    dispatcher.addCollector(new RemedyIncidentManagementCollector(config, configParser));
                }
                if (config.getRequestType().equalsIgnoreCase(RequestType.CM.getValues())) {
                    dispatcher.addCollector(new RemedyChangeManagementCollector(config, configParser));
                }
            }
        });
        dispatcher.run();
    }

    public static void main(String[] args) {
        //EventSinkAPI eventSinkAPI = new EventSinkAPI();
        //eventSinkAPI.emit(Util.event(Constants.REMEDY_PLUGIN_TITLE_MSG, "Up", "", Event.EventSeverity.INFO.toString()));
        PluginRunner plugin = new PluginRunner("com.bmc.truesight.meter.plugin.remedy.RemedyPlugin");
        plugin.run();
    }

    @Override
    public void setMeasureOutput(MeasurementSink output) {
    }
}
