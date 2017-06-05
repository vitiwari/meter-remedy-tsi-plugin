/**
 *
 * @author Santosh Patil
 * @Date 22-05-2017
 */
package com.bmc.truesight.meter.plugin.remedy;

import com.boundary.plugin.sdk.PluginConfiguration;
import java.util.ArrayList;

public class RemedyPluginConfiguration implements PluginConfiguration {

    private ArrayList<RemedyPluginConfigurationItem> items;
    private int pollInterval;

    public RemedyPluginConfiguration() {

    }

    public ArrayList<RemedyPluginConfigurationItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<RemedyPluginConfigurationItem> items) {
        this.items = items;
    }

    public int getPollInterval() {
        return pollInterval;
    }

    public void setPollInterval(int pollInterval) {
        this.pollInterval = pollInterval;
    }

}
