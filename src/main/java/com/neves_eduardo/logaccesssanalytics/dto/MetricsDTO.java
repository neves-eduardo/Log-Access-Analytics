package com.neves_eduardo.logaccesssanalytics.dto;

import java.util.Map;

public class MetricsDTO {
    private Map<String,Double> topURLs;
    private Map<String,Double> topURLsByRegion;
    private Map<String,Double> bottomURL;
    private Map<String,Double> topURLByTime;
    private Map<Double,Double> mostAccessedMoment;

    public MetricsDTO(Map<String, Double> topURLs,
                      Map<String, Double> topURLsByRegion,
                      Map<String, Double> bottomURL,
                      Map<String, Double> topURLByTime,
                      Map<Double, Double> mostAccessedMoment) {
        this.topURLs = topURLs;
        this.topURLsByRegion = topURLsByRegion;
        this.bottomURL = bottomURL;
        this.topURLByTime = topURLByTime;
        this.mostAccessedMoment = mostAccessedMoment;
    }

    public Map<String, Double> getTopURLs() {
        return topURLs;
    }

    public void setTopURLs(Map<String, Double> topURLs) {
        this.topURLs = topURLs;
    }

    public Map<String, Double> getTopURLsByRegion() {
        return topURLsByRegion;
    }

    public void setTopURLsByRegion(Map<String, Double> topURLsByRegion) {
        this.topURLsByRegion = topURLsByRegion;
    }

    public Map<String, Double> getBottomURL() {
        return bottomURL;
    }

    public void setBottomURL(Map<String, Double> bottomURL) {
        this.bottomURL = bottomURL;
    }

    public Map<String, Double> getTopURLByTime() {
        return topURLByTime;
    }

    public void setTopURLByTime(Map<String, Double> topURLByTime) {
        this.topURLByTime = topURLByTime;
    }

    public Map<Double, Double> getMostAccessedMoment() {
        return mostAccessedMoment;
    }

    public void setMostAccessedMoment(Map<Double, Double> mostAccessedMoment) {
        this.mostAccessedMoment = mostAccessedMoment;
    }
}
