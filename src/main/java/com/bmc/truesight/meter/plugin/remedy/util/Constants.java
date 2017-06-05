package com.bmc.truesight.meter.plugin.remedy.util;

/**
 *
 * @author Santosh Patil
 * @Date 22-05-2017
 */
public class Constants {

    public static int REMEDY_FETCH_MAX_RECORDS = 5000;
    public static String REMEDY_START_JSON_VALUES = "{ \"jsonrpc\": \"2.0\", \"params\": { \"data\": \"_bevent:";
    public static String REMEDY_END_JSON_VALUES = " },\"method\": \"event\",\"id\": 1 }";
    public static String REMEDY_DOUBLE_QUOTA_END = "\"";
    public static String REMEDY_PLUGIN_STARTED_MSG = Constants.REMEDY_START_JSON_VALUES + "Remedy Plugin started|m:Started Remedy plugin|t:info|tags:" + Constants.REMEDY_DOUBLE_QUOTA_END + Constants.REMEDY_END_JSON_VALUES;
    public static String REMEDY_STATUS_CLOSED = "Closed";
    public static final String REMEDY_HELP_DESK_FORM = "HPD:Help Desk";
    public static final String REMEDY_EQUAL = "=";
    public static final String REMEDY_COMMA = ",";
    public static final String REMEDY_PIPE = "|";
    public static final int TSI_STATUS_CODE = 202;
    public static final int TSI_DEFAULT_RETRIES = 3;
    public static final long TSI_DEFAULT_WAIT_TIME_IN_MILLI = 2000;
    public static String REMEDY_CONFIG_INVALID_JSON = "The configuration ({0}) is invalid JSON";
    public static String REMEDY_CONFIG_PROPERTY_NOT_FOUND = "Either the configuration file does not contain 'config' property, or fields are not correct";
    public static String REMEDY_PAYLOAD_PROPERTY_NOT_FOUND = "Either the configuration file does not contain 'payload' property, or fields are not correct";
    public static String REMEDY_PLACEHOLDER_PROPERTY_NOT_CORRECT = "The fields  for property {0} are not correct";
    public static String REMEDY_CONFIG_VALIDATION_FAILED = "The fields for config elements are empty, it should be nonempty";
    public static String PAYLOAD_PLACEHOLDER_DEFINITION_MISSING = "The definition for payload placeholder {0} is missing in the configuration file";
    public static String REMEDY_PLUGIN_TITLE_MSG = "Remedy Plugin";
    public static String REMEDY_LOGIN_FAILED = "Login failed to Remedy Server, ({0})";
    public static int REMEDY_CHUNK_SIZE = 2000;

    public static final int SUBMIT_DATE_FIELD = 3;
    public static final String REMEDY_PROXY_EVENT_JSON_START_STRING = "{ \"jsonrpc\": \"2.0\", \"method\": \"proxy_event\", \"params\": {  \"data\":";
    public static final String REMEDY_PROXY_EVENT_JSON_END_STRING = " } }";
    public static final String REMEDY_CHANGE_DESK_FORM = "HPD:Help Desk";
    public static final String REMEDY_CLOSE_DATE = "@CREATED_AT";
    public final static String REMEDY_CHG_FORM_INFRASTRUCTURE_CHANGE = "CHG:Infrastructure Change";
    public final static String REMEDY_IM_NO_DATA_AVAILABLE="No data available for incident management";
    public final static String REMEDY_CM_NO_DATA_AVAILABLE="No data available for change management";
    public static final String REMEDY_LAST_UPDATED_DATE = "@LASTUPDATEDATE";
    public static final String REMEDY_STATUS_FIELD = "@STATUS";

}
