package com.bmc.truesight.remedy.beans;

import java.util.List;

public class Configuration {
	
	private String tsiApiToken;
    private List<Integer> conditionFields;
    private List<Integer> statusCondition;

    public List<Integer> getStatusCondition() {
        return statusCondition;
    }

    public void setStatusCondition(List<Integer> statusCondition) {
        this.statusCondition = statusCondition;
    }
    public List<Integer> getConditionFields() {
        return conditionFields;
    }

    public void setConditionFields(List<Integer> conditionFields) {
        this.conditionFields = conditionFields;
    }

	public String getTsiApiToken() {
		return tsiApiToken;
	}

	public void setTsiApiToken(String tsiApiToken) {
		this.tsiApiToken = tsiApiToken;
	}

}
