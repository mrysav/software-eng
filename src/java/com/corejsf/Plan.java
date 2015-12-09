package com.corejsf;

public class Plan {

    private final String planName;
    private final String planCarrier;
    private final String planCost;
    private final String planURL;

    public String getPlanName() {
        return planName;
    }

    public String getPlanCarrier() {
        return planCarrier;
    }

    public String getPlanCost() {
        return planCost;
    }

    public String getPlanURL() {
        return planURL;
    }

    public Plan(String name, String carrier, String cost, String url) {
        planName = name;
        planCarrier = carrier;
        planCost = cost;
        planURL = url;
    }
}
