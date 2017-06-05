/**
 *
 * @author Santosh Patil
 * @Date 22-05-2017
 */
package com.bmc.truesight.meter.plugin.remedy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bmc.arsys.api.ARException;
import com.bmc.arsys.api.ARServerUser;
import com.bmc.arsys.api.ArithmeticOrRelationalOperand;
import com.bmc.arsys.api.DataType;
import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.OutputInteger;
import com.bmc.arsys.api.QualifierInfo;
import com.bmc.arsys.api.RelationalOperationInfo;
import com.bmc.arsys.api.SortInfo;
import com.bmc.arsys.api.Timestamp;
import com.bmc.arsys.api.Value;
import com.bmc.truesight.meter.plugin.remedy.util.ConfigParser;
import com.bmc.truesight.meter.plugin.remedy.util.Constants;
import com.bmc.truesight.meter.plugin.remedy.util.RemedyEntryEventAdapter;
import com.bmc.truesight.meter.plugin.remedy.util.StringUtils;
import com.bmc.truesight.meter.plugin.remedy.util.Util;
import com.bmc.truesight.remedy.beans.Payload;
import com.boundary.plugin.sdk.Collector;
import com.boundary.plugin.sdk.Event;
import com.boundary.plugin.sdk.EventSinkAPI;
import com.boundary.plugin.sdk.EventSinkStandardOutput;
import com.boundary.plugin.sdk.Measurement;

public class RemedyIncidentManagementCollector implements Collector {

    private static final Logger LOG = LoggerFactory
            .getLogger(RemedyPlugin.class);
    private final RemedyPluginConfigurationItem config;
    private final ConfigParser configParser;
    private RemedyEntryEventAdapter remedyEntryEventAdapter = new RemedyEntryEventAdapter();

    public RemedyIncidentManagementCollector(RemedyPluginConfigurationItem config, ConfigParser configParser) {
        this.config = config;
        this.configParser = configParser;
    }

    private ARServerUser createARServerContext() {
        ARServerUser arServerContext = new ARServerUser();
        if (this.config.getPort() != null) {
            arServerContext.setPort(this.config.getPort());
        }
        arServerContext.setServer(this.config.getHostName());
        arServerContext.setUser(this.config.getUserName());
        arServerContext.setPassword(this.config.getPassword());
        return arServerContext;
    }

    @Override
    public Measurement[] getMeasures() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void run() {
    	HttpEventSinkAPI eventSinkAPI = new HttpEventSinkAPI();
    	EventSinkStandardOutput eventSinkAPIstd = new EventSinkStandardOutput();
        
        while (true) {
            try {
                ARServerUser arServerContext = createARServerContext();
                Calendar cal = null;
                try {
                	arServerContext.login();
                    int chunkSize = Constants.REMEDY_CHUNK_SIZE;
                    int startFrom = 1;
                    int iteration = 1;
                    OutputInteger nMatches = new OutputInteger();
                    boolean readNext = true;
                    cal = Calendar.getInstance();
                    //cal.add(Calendar.MILLISECOND, (0 - config.getPollInterval()));
                    //Only Testing perpose
                    cal.add(Calendar.MILLISECOND, (0 - config.getPollInterval()));
                    while (readNext) {
                        List<Payload> eventList = readRemedyIncidentTickets(arServerContext, startFrom, chunkSize, nMatches, cal.getTime());
                        if (eventList.size() > 0) {
                        	eventSinkAPI.pushBulkEventsToTSI(eventList, "52a90a69-242b-4a5f-8685-44eb1d42a0f8", "https://api.truesight-staging.bmc.com/v1/events");
                        	/*eventList.forEach(event -> {
                                Gson gson = new Gson();
                                String eventJson = gson.toJson(event, Object.class);
                                StringBuilder sendEventToTSI = new StringBuilder();
                                sendEventToTSI.append(Constants.REMEDY_PROXY_EVENT_JSON_START_STRING).append(eventJson).append(Constants.REMEDY_PROXY_EVENT_JSON_END_STRING);
                                LOG.info(sendEventToTSI.toString());
                                System.out.println("com.bmc.truesight.meter.plugin.remedy.RemedyIncidentManagementCollector.run()" + sendEventToTSI.toString());
                                eventSinkAPI.emit(sendEventToTSI.toString());
                            });*/
                        } else {
                        	eventSinkAPIstd.emit(Util.eventMeterTSI(Constants.REMEDY_PLUGIN_TITLE_MSG, Constants.REMEDY_IM_NO_DATA_AVAILABLE, configParser, Event.EventSeverity.INFO.toString()));
                        }
                        if (nMatches.longValue() <= (startFrom + chunkSize)) {
                            readNext = false;
                        }
                        iteration++;
                        startFrom = startFrom + chunkSize;
                    }

                } catch (Exception e) {
                    LOG.error("Exception occure while fetching the data", e);
                    eventSinkAPIstd.emit(Util.eventMeterTSI(Constants.REMEDY_PLUGIN_TITLE_MSG, e.getMessage(), configParser, Event.EventSeverity.ERROR.toString()));
                    //eventSinkAPIstd.emit(Util.event(Constants.REMEDY_PLUGIN_TITLE_MSG, e.getMessage(), config.getHostName(), Event.EventSeverity.ERROR.toString()));
                } finally {
                    arServerContext.logout();
                }
                Thread.sleep(config.getPollInterval());
            } catch (InterruptedException ex) {
            	eventSinkAPIstd.emit(Util.eventMeterTSI(Constants.REMEDY_PLUGIN_TITLE_MSG, ex.getMessage(), configParser, Event.EventSeverity.ERROR.toString()));
                //eventSinkAPI.emit(Util.event(Constants.REMEDY_PLUGIN_TITLE_MSG, ex.getMessage(), config.getHostName(), Event.EventSeverity.ERROR.toString()));
            }
        }
    }

    public List<Payload> readRemedyIncidentTickets(ARServerUser arServerContext, int startFrom, int chunkSize, OutputInteger nMatches, final Date date) {

        //keeping as set to avoid duplicates
        Set<Integer> fieldsList = new HashSet<>();
        configParser.getFieldItemMap().values().forEach(fieldItem -> {
            fieldsList.add(fieldItem.getFieldId());
        });
        EventSinkStandardOutput eventSinkAPIstd = new EventSinkStandardOutput();
        int[] queryFieldsList = new int[fieldsList.size()];
        int index = 0;
        for (Integer i : fieldsList) {
            queryFieldsList[index++] = i;
        }

        Date newDate = new Date();
        QualifierInfo qualInfoF = null;
        if (configParser.getConfiguration().getConditionFields() != null && configParser.getConfiguration().getConditionFields().size() > 0) {
            for (int fieldId : configParser.getConfiguration().getConditionFields()) {
                QualifierInfo qualInfo1 = buildFieldValueQualification(fieldId,
                        new Value(new Timestamp(date), DataType.TIME), RelationalOperationInfo.AR_REL_OP_GREATER_EQUAL);

                QualifierInfo qualInfo2 = buildFieldValueQualification(fieldId,
                        new Value(new Timestamp(newDate), DataType.TIME), RelationalOperationInfo.AR_REL_OP_LESS_EQUAL);

                QualifierInfo qualInfo = new QualifierInfo(QualifierInfo.AR_COND_OP_AND, qualInfo1, qualInfo2);

                if (qualInfoF != null) {
                    qualInfoF = new QualifierInfo(QualifierInfo.AR_COND_OP_OR, qualInfoF, qualInfo);
                } else {
                    qualInfoF = qualInfo;
                }

            }
        }
        List<SortInfo> sortOrder = new ArrayList<>();
        List<Entry> entryList = new ArrayList<>();
        try {
            entryList = arServerContext.getListEntryObjects(Constants.REMEDY_HELP_DESK_FORM, qualInfoF,
                    startFrom, chunkSize, sortOrder, queryFieldsList, false, nMatches);
        } catch (ARException ex) {
        	eventSinkAPIstd.emit(Util.eventMeterTSI(Constants.REMEDY_PLUGIN_TITLE_MSG, StringUtils.format(Constants.REMEDY_LOGIN_FAILED, new Object[]{ex.getMessage()}), configParser, Event.EventSeverity.ERROR.toString()));
        }
        List<Payload> payloadList = new ArrayList<>();
        if (entryList.size() > 0) {
            entryList.forEach(entry -> {
               // if (Util.statusFilter(configParser, entry, configParser.getConfiguration().getStatusCondition())) {
                    payloadList.add(remedyEntryEventAdapter.convertIncidentEntryToPayload(configParser, entry));
               // }
            });
        }
        return payloadList;
    }

    /**
     * Prepare qualification "<fieldId>=<Value>"
     *
     * @return QualifierInfo
     */
    private QualifierInfo buildFieldValueQualification(int fieldId, Value value, int relationalOperation) {
        ArithmeticOrRelationalOperand leftOperand = new ArithmeticOrRelationalOperand(fieldId);
        ArithmeticOrRelationalOperand rightOperand = new ArithmeticOrRelationalOperand(value);
        RelationalOperationInfo relationalOperationInfo = new RelationalOperationInfo(relationalOperation, leftOperand,
                rightOperand);
        QualifierInfo qualification = new QualifierInfo(relationalOperationInfo);
        return qualification;
    }
}
