/**
 *
 * @author Santosh Patil
 * @Date 22-05-2017
 */
package com.bmc.truesight.meter.plugin.remedy;

import com.bmc.truesight.meter.plugin.remedy.util.Constants;

public class RemedyPluginConfigurationItem {

    private String hostName;
    private Integer port;
    private String userName;
    private String password;
    private long maxRecords;
    private int pollInterval;
    private String requestType;
    private String fileds[];

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String[] getFileds() {
        return fileds;
    }

    public void setFileds(String[] fileds) {
        this.fileds = fileds;
    }

    public RemedyPluginConfigurationItem() {
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public long getMaxRecords() {
        return maxRecords;
    }

    public void setMaxRecords(long maxRecords) {
        this.maxRecords = Constants.REMEDY_FETCH_MAX_RECORDS;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPollInterval() {
        return pollInterval;
    }

    public void setPollInterval(int pollInterval) {
        this.pollInterval = pollInterval;
    }
}
