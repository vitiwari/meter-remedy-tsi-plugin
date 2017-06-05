package com.bmc.truesight.remedy.beans;

import java.util.List;

public class Configuration {

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

}
