/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmc.truesight.meter.plugin.remedy;

import com.bmc.truesight.meter.plugin.remedy.util.ConfigParser;
import com.bmc.truesight.meter.plugin.remedy.util.Constants;
import com.bmc.truesight.meter.plugin.remedy.util.RequestType;
import com.bmc.truesight.meter.plugin.remedy.util.Util;
import com.bmc.truesight.remedy.beans.Events;
import com.bmc.truesight.remedy.exception.ParsingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author santopat
 */
public class Check {

    public static void main(String arg[]) throws IOException {
        String values = Constants.REMEDY_START_JSON_VALUES + "_bevent:Plugin started|m:Started remedy plugin|t:info|tags:" + Constants.REMEDY_DOUBLE_QUOTA_END + Constants.REMEDY_END_JSON_VALUES;
        String val = "{  \"payload\": {    \"title\": \"@TITLE\",    \"fingerprintFields\": [  "
                + "    \"IncidentNumber\"    ],    \"severity\": \"@SEVERITY\",    \"status\": \"@STATUS\",  "
                + "  \"properties\": {      \"Assignee\": \"@ASSIGNEE\",      \"IncidentNumber\": \"@INCIDENT_NUMBER\",   "
                + "   \"CI\": \"@CI\",      \"Priority\": \"@PRIORITY\",      \"Priority Weight\": \"@WEIGHT\",      \"app_id\": \"@APP_ID\"    }, "
                + "   \"createdAt\": \"@CREATED_AT\",    \"eventClass\": \"Incident\",    \"source\": {      \"name\": \"Remedy\",    "
                + "  \"type\": \"Remedy\",      \"ref\": \"Remedy\"    },    \"sender\": {      \"name\": \"Remedy\",      \"type\": \"Remedy\",  "
                + "    \"ref\": \"Remedy\"    }  },  \"@SEVERITY\": {    \"1000\": \"Critical\",    \"2000\": \"High\",    \"3000\": \"Medium\",  "
                + "  \"4000\": \"Low\"  },  \"@PRIORITY\": {    \"0\": \"Critical\",    \"1\": \"High\",    \"2\": \"Medium\", "
                + "   \"3\": \"Low\"  },  \"@STATUS\": {    \"0\": \"New\",    \"1\": \"Assigned\",    \"2\": \"In Progress\",  "
                + "  \"3\": \"Pending\",    \"4\": \"Resolved\",    \"5\": \"Closed\",    \"6\": \"Cancelled\"  },  \"@APP_ID\": {   "
                + " \"BMC_APPLICATION\": \"BMC APPLICATION\"  }}";
        System.out.println("com.bmc.truesight.meter.plugin.remedy.Check.main()" + val);
        //JsonElement element = Util.converStringIntoJsonObject(val);
        //JsonObject jsonObject = element.getAsJsonObject();
        //JsonObject jsonObject1 = jsonObject.getAsJsonObject("payload");
        //System.out.println("Patil-------" + jsonObject1.get("properties").getAsJsonObject().get("IncidentNumber"));
        //List keys = new ArrayList();
        /*try {
            keys = Util.getKeysFromJson(jsonObject1.get("properties").getAsJsonObject().toString());
        } catch (Exception ex) {
            Logger.getLogger(Check.class.getName()).log(Level.SEVERE, null, ex);
        }*/

 /*JsonObject jsonObject23 = jsonObject1.get("properties").getAsJsonObject();
        System.out.println("com.bmc.truesight.meter.plugin.remedy.Check.main()" + keys);
        
          // Check whether jsonElement is JsonObject or not
        if (jsonObject.isJsonObject()) {
            Set<Entry<String, JsonElement>> ens = ((JsonObject) jsonObject23).entrySet();
            if (ens != null) {
                // Iterate JSON Elements with Key values
                for (Entry<String, JsonElement> en : ens) {
                    System.out.println("KUMAR" +en.getKey() + " : ");
                    System.out.println("Santosh"+en.getValue());
                }
            }*/
        System.out.println("====" + RequestType.IM.getValues());
        //System.out.println("REMEDY_SEVERITY_GET_OBEJCT{}" + Util.getKeyAndValueForTSI(jsonObject,Constants.REMEDY_SEVERITY_GET_OBEJCT));
        //System.out.println("REMEDY_PRIORITY_GET_OBEJCT {}" + Util.getKeyAndValueForTSI(jsonObject,Constants.REMEDY_PRIORITY_GET_OBEJCT));
        //System.out.println("REMEDY_STATUS_GET_OBEJCT {}" + Util.getKeyAndValuePair(jsonObject,Constants.REMEDY_STATUS_GET_OBEJCT));
        /*Map<String, String> finalValues = new HashMap();
        finalValues = Util.getKeyAndValuePair(jsonObject1,Constants.REMEDY_SOURCE_GET_OBJECT);
        String str = Util.properties("0", "sds", "sdf", "dfgg", "tyr", "dfg", "sfsdf", "asdas", "dfdf");
        System.out.println("com.bmc.truesight.meter.plugin.remedy.Check.main()" +str );
        String str1 = "1468833954]";
        String replaceString=str1.replace("[Timestamp=","");//replaces all occurrences of "is" to "was"  
        replaceString= replaceString.replace("]","");
        System.out.println("com.bmc.truesight.meter.plugin.remedy.Check.main()" +replaceString );*/

        //String current = new java.io.File(".").getCanonicalPath();
        //System.out.println("Current dir:" + current);
        //String currentDir = System.getProperty("user.dir");
        //System.out.println("Current dir using System:" + currentDir);
        //Gson gson = new Gson();
        //File file = new File("../meter.conf");
        //FileInputStream fis = new FileInputStream(file);
        //byte[] data = new byte[(int) file.length()];
        //fis.read(data);
        //fis.close();
        //String str = new String(data, "UTF-8");
        //JsonElement meterConf = Util.converStringIntoJsonObject(str);
        //JsonObject jsonObject23 = meterConf.getAsJsonObject().getAsJsonObject("premium_api");
        //System.out.println("com.bmc.truesight.meter.plugin.remedy.Check.main()" + jsonObject23.get("host").getAsString());
        //System.out.println("com.bmc.truesight.meter.plugin.remedy.Check.main()" + jsonObject23.get("token").getAsString());
        String eventContent = "[\n"
                + "  {\n"
                + "    \"title\": \"REMEDY==========980o9809809\",\n"
                + "    \"fingerprintFields\": [\n"
                + "      \"INC000000000004\"\n"
                + "    ],\n"
                + "    \"severity\": \"Critical\",\n"
                + "    \"status\": \"Closed\",\n"
                + "    \"properties\": {\n"
                + "      \"Assignee\": \"Francie Stafford\",\n"
                + "      \"IncidentNumber\": \"INC000000000004\",\n"
                + "      \"CI\": \"BMC_APPLICATION\",\n"
                + "      \"Priority\": \"High\",\n"
                + "      \"PriorityWeight\": \"18\",\n"
                + "      \"app_id\": \"BMC APPLICATION\"\n"
                + "    },\n"
                + "    \"createdAt\": \"1466804216\",\n"
                + "    \"eventClass\": \"Incident\",\n"
                + "    \"source\": {\n"
                + "      \"name\": \"Remedy\",\n"
                + "      \"type\": \"Remedy\",\n"
                + "      \"ref\": \"Remedy\"\n"
                + "    },\n"
                + "    \"sender\": {\n"
                + "      \"name\": \"Remedy\",\n"
                + "      \"type\": \"Remedy\",\n"
                + "      \"ref\": \"Remedy\"\n"
                + "    }\n"
                + "  },\n"
                + "  {\n"
                + "    \"title\": \"REMEDY==========\",\n"
                + "    \"fingerprintFields\": [\n"
                + "      \"INC000000000004\"\n"
                + "    ],\n"
                + "    \"severity\": \"Critical\",\n"
                + "    \"status\": \"Closed\",\n"
                + "    \"properties\": {\n"
                + "      \"Assignee\": \"Francie Stafford\",\n"
                + "      \"IncidentNumber\": \"INC000000000004\",\n"
                + "      \"CI\": \"BMC_APPLICATION\",\n"
                + "      \"Priority\": \"High\",\n"
                + "      \"PriorityWeight\": \"18\",\n"
                + "      \"app_id\": \"BMC APPLICATION\"\n"
                + "    },\n"
                + "    \"createdAt\": \"1466804216\",\n"
                + "    \"eventClass\": \"Incident\",\n"
                + "    \"source\": {\n"
                + "      \"name\": \"Remedy\",\n"
                + "      \"type\": \"Remedy\",\n"
                + "      \"ref\": \"Remedy\"\n"
                + "    },\n"
                + "    \"sender\": {\n"
                + "      \"name\": \"Remedy\",\n"
                + "      \"type\": \"Remedy\",\n"
                + "      \"ref\": \"Remedy\"\n"
                + "    }\n"
                + "  }\n"
                + "]";

        //String eventContent = "[{\"fingerprintFields\":[\"@title\"],\"source\":{\"name\":\"a fi/two\",\"ref\":\"mymeter tsi\",\"type\":\"host tsi\"},\"message\":\"my Bulk Test message tsi_01\",\"title\":\"BULKY Test_01\",\"properties\":{\"app_id\":\"bulktestAPP\"},\"severity\":\"INFO\",\"eventClass\":\"bulktest\",\"status\":\"OPEN\"},{\"fingerprintFields\":[\"@title\"],\"source\":{\"name\":\"slash/dash\",\"ref\":\"mymeter tsi\",\"type\":\"host tsi\"},\"message\":\"my Bulk Test message tsi_02\",\"title\":\"BULKY Test_02\",\"properties\":{\"app_id\":\"bulktestAPP\"},\"severity\":\"INFO\",\"eventClass\":\"bulktest\",\"status\":\"OPEN\"}]";
        /*String url = "https://api.truesight-staging.bmc.com/v1/events";
        String token = "be300968-ac4c-43f2-9bb2-b0e82b84ccab";
        RemedyPluginConfigurationItem config = new RemedyPluginConfigurationItem();
        config.setHostName("api.truesight-staging.bmc.com");
        String source = "REMEDY";
        HttpClient.pushBulkEvents(eventContent,token,url);
        System.out.println("com.bmc.truesight.meter.plugin.remedy.Check.main()" + Util.encodeBase64(""+":"+token));
        
         *///System.out.println("com.bmc.truesight.meter.plugin.remedy.Check.main()" + Util.errorEeventFormat("Remedy Plugin", "exception occured", "bmc.com", "info"));
        //String url = "https://api.truesight-staging.bmc.com/v1/events";
        //String token = "be300968-ac4c-43f2-9bb2-b0e82b84ccab";
        //System.out.println("com.bmc.truesight.meter.plugin.remedy.Check.main()" + eventContent);
        //String str = "[Timestamp=3d1486026317]";
        //str =str.replace("[Timestamp=", "").trim();
        //HttpClient.pushBulkEvents(eventContent, HttpClient.encodeBase64(""+":"+token), url);
        //System.out.println("HttpClient" + HttpClient.pushBulkEvents(eventContent, HttpClient.encodeBase64(""+":"+token), url));
        //System.out.println("str===" + str.replace("]", ""));
     
        /*List<String> fingerprintFields = new ArrayList<>();
        Map<String,String> sender = new HashMap<>();
        sender.put("ref","asdasd");
        sender.put("host","asdasdasd");
        Map<String,String> source = new HashMap<>();
        source.put("ref","asdasd");
        source.put("host","asdasdasd");
        Map<String,String> properties = new HashMap<>();
        properties.put("app_id","asdasd");
        properties.put("properties","asdasdasd");
        fingerprintFields.add("@title");
        Events events = new Events();
        events.setTitle("Santosh Patil");
        events.setFingerprintFields(fingerprintFields);
        events.setSender(sender);
        events.setSource(source);
        events.setMessage("No data Valiable");
        events.setSeverity("INFO");
        events.setCreatedAt("");
        events.setProperties(properties);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        System.out.println(gson.toJson(events));*/

    }
}
