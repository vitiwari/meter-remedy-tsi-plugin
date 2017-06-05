package com.bmc.truesight.meter.plugin.remedy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bmc.arsys.api.Entry;
import com.bmc.arsys.api.Value;
import com.bmc.thirdparty.org.apache.commons.codec.binary.Base64;
import com.bmc.truesight.remedy.beans.Events;
import com.bmc.truesight.remedy.beans.FieldItem;
import com.bmc.truesight.remedy.beans.Payload;
import com.boundary.plugin.sdk.Event;
import com.boundary.plugin.sdk.EventSinkStandardOutput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author Santosh Patil
 * @Date 22-05-2017
 */
public class Util {

    private static final Logger LOG = LoggerFactory
            .getLogger(Util.class);

    public static String getFieldValues(String fieldValue[]) {
        StringBuilder fieldValues = new StringBuilder();
        if (fieldValues != null && fieldValue.length > 1) {
            for (String val : fieldValue) {
                fieldValues.append(val);
            }
        }
        return fieldValues.toString();
    }

    public static Map<JsonElement,String> getKeyAndValueFromJsonObjectForRemedy(JsonObject jsonObjects, String jsonKey) {
        Map<JsonElement,String> values = new HashMap<>();
        JsonObject jsonObject = jsonObjects.get(jsonKey).getAsJsonObject();
        if (jsonObject.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> elements = ((JsonObject) jsonObject).entrySet();
            if (elements != null) {
                // Iterate JSON Elements with Key values
                elements.stream().forEach((en) -> {
                    values.put(en.getValue(), en.getKey());
                });
            }
        }
        return values;
    }

    public static String encodeBase64(final String encodeToken) {
        byte[] encoded = Base64.encodeBase64(encodeToken.getBytes());
        return new String(encoded);
    }

    public static void send(final Event.EventSeverity severity, final String strShortSummary, final String strSummary, final String hostName, final String source) {
        EventSinkStandardOutput output = new EventSinkStandardOutput();
        Event event = new Event(severity, strShortSummary,
                strSummary, hostName, source, null);
        output.emit(event);
    }

    public static String event(final String title, final String msg, final String hostName, final String severitiy) {
        StringBuilder format = new StringBuilder();
        format.append(Constants.REMEDY_START_JSON_VALUES).append(title).
                append(Constants.REMEDY_PIPE).append("m:").append(msg).
                append(Constants.REMEDY_PIPE).append("s:REMEDY").append(Constants.REMEDY_PIPE).append("h:").append(hostName)
                .append(Constants.REMEDY_PIPE).append("t:").append(severitiy).append("|tags:").append(Constants.REMEDY_DOUBLE_QUOTA_END).append(Constants.REMEDY_END_JSON_VALUES);
        return format.toString();
    }

    public static String remove(String removeString, String originalString) {

        originalString = originalString.replace(removeString, "").trim();
        return originalString;
    }
    public static Event eventMeterTSI(final String title, final String message, ConfigParser configParser, String severity) {
        Event event = new Event(title,message);
        return event;
    }
    public static String eventTSI(final String title, final String message, ConfigParser configParser, String severity) {
        StringBuilder sendEventToTSI = new StringBuilder();
        try {
            Gson gson = null;
            Events events = new Events();
            List<String> fingerprintFields = new ArrayList<>();
            Map<String, String> properties = new HashMap<>();
            properties = configParser.getPayload().getProperties();
            String eventClass = configParser.getPayload().getEventClass();
            fingerprintFields.add("@title");
            events.setTitle(message);
            events.setFingerprintFields(fingerprintFields);
            events.setSender(configParser.getPayload().getSender());
            events.setSource(configParser.getPayload().getSource());
            events.setMessage(message);
            events.setSeverity(severity);
            events.setEventClass(eventClass);
            events.setCreatedAt("");
            events.setProperties(properties);
            events.setStatus("");
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
            sendEventToTSI.append(Constants.REMEDY_PROXY_EVENT_JSON_START_STRING).append(gson.toJson(events)).append(Constants.REMEDY_PROXY_EVENT_JSON_END_STRING);

        } catch (Exception ex) {
            LOG.error("Exception occure while sending error evenst", ex.getMessage());
        }
        return sendEventToTSI.toString();
    }

    public static boolean statusFilter(ConfigParser configParser, Entry entry, List<Integer> statusList) {
        boolean isStatus = true;
        try {
            Integer statusValue = null;
            if (statusList != null && statusList.size() > 0) {
                Payload payload = new Payload(configParser.getPayload());
                statusValue = getValueFromEntry(configParser, entry, payload.getStatus());
                isStatus = contains(statusList, statusValue);
            }
        } catch (Exception ex) {
            LOG.error("Exception occured in status filter{}", ex.getMessage());
        }
        return isStatus;
    }

    public static Integer getValueFromEntry(ConfigParser configParser, Entry entry, String placeholder) {
        Integer val = null;
        if (placeholder.startsWith("@")) {
            FieldItem fieldItem = configParser.getFieldItemMap().get(placeholder);
            Value value = entry.get(fieldItem.getFieldId());
            if (value != null && value.getValue() != null) {
                val = Integer.parseInt(value.getValue().toString());
            }
        }
        return val;
    }

    public static boolean contains(List<Integer> list, Integer val) {
        if (list.stream().anyMatch((item) -> (Objects.equals(item, val)))) {
            return true;
        }
        return false;
    }
}
