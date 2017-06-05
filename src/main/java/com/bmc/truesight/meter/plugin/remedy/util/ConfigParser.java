package com.bmc.truesight.meter.plugin.remedy.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bmc.truesight.remedy.beans.Configuration;
import com.bmc.truesight.remedy.beans.FieldItem;
import com.bmc.truesight.remedy.beans.Payload;
import com.bmc.truesight.remedy.exception.ParsingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class parses the configuration file and
 *
 * @author Santosh Patil
 */
public class ConfigParser {

    private static final Logger log = LoggerFactory.getLogger(ConfigParser.class);

    private String strJson;
    private Configuration configuration;
    private Payload payload;
    private Map<String, FieldItem> fieldItemMap;

    public ConfigParser(String strJson) {
        this.strJson = strJson;
        this.fieldItemMap = new HashMap<String, FieldItem>();
    }

    /**
     * Used to parse the configuration in case of configuration available as
     * json String
     *
     * @param configJson
     * @throws ParsingException
     */
    public void readParseConfigJson(String configJson) throws ParsingException {
        parse(configJson);
    }

    /**
     * Used to parse the configuration in case of configuration available as
     * json file
     *
     * @param configJson
     * @throws ParsingException
     */
    private void parse(String strJson) throws ParsingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(strJson);
        } catch (IOException e) {
            throw new ParsingException(StringUtils.format(Constants.REMEDY_CONFIG_INVALID_JSON, new Object[]{this.strJson}));
        }

        // Read the config details and map to pojo
        String configString;
        try {
            JsonNode configuration = rootNode.get("config");
            configString = mapper.writeValueAsString(configuration);
            this.configuration = mapper.readValue(configString, Configuration.class);
        } catch (IOException e) {
            throw new ParsingException(StringUtils.format(Constants.REMEDY_CONFIG_PROPERTY_NOT_FOUND, new Object[]{}));
        }

        // Read the payload details and map to pojo
        try {
            JsonNode payloadNode = rootNode.get("payload");
            String payloadString = mapper.writeValueAsString(payloadNode);
            this.setPayload(mapper.readValue(payloadString, Payload.class));
        } catch (IOException e) {
            throw new ParsingException(StringUtils.format(Constants.REMEDY_PAYLOAD_PROPERTY_NOT_FOUND, new Object[]{}));
        }

        // Iterate over the properties and if it starts with '@', put it to
        // itemValueMap
        Iterator<Entry<String, JsonNode>> nodes = rootNode.fields();
        while (nodes.hasNext()) {
            Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
            if (entry.getKey().startsWith("@")) {
                try {
                    String placeholderNode = mapper.writeValueAsString(entry.getValue());
                    FieldItem placeholderDefinition = mapper.readValue(placeholderNode, FieldItem.class);
                    fieldItemMap.put(entry.getKey(), placeholderDefinition);
                } catch (IOException e) {
                    throw new ParsingException(StringUtils.format(Constants.REMEDY_PAYLOAD_PROPERTY_NOT_FOUND, new Object[]{entry.getKey()}));
                }
            }

        }

    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Payload getPayload() {
        return payload;
    }

    public Map<String, FieldItem> getFieldItemMap() {
        return fieldItemMap;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

}
