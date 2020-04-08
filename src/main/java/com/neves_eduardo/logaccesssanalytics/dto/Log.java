package com.neves_eduardo.logaccesssanalytics.dto;

import java.util.UUID;

public class Log {
    private String url;
    private long timestamp;
    private UUID id;
    private int region;

    public Log() {
    }

    public Log(String url, long timestamp, UUID id, int region) {
        this.url = url;
        this.timestamp = timestamp;
        this.id = id;
        this.region = region;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }
}
