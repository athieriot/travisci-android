package com.github.athieriot.android.travisci.core.entity;

import org.joda.time.DateTime;

import java.io.Serializable;

public class Worker implements Serializable {

    private Long id;

    private String name;

    private String host;

    private String state;

    private DateTime last_seen_at;

    private Payload payload;

    private String last_error;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public DateTime getLast_seen_at() {
        return last_seen_at;
    }

    public void setLast_seen_at(DateTime last_seen_at) {
        this.last_seen_at = last_seen_at;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getLast_error() {
        return last_error;
    }

    public void setLast_error(String last_error) {
        this.last_error = last_error;
    }
}

