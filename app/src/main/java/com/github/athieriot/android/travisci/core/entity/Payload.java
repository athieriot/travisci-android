package com.github.athieriot.android.travisci.core.entity;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;

import java.io.Serializable;

public class Payload implements Serializable, Comparable<Payload> {

    private String type;

    private Build build;

    private Job job;

    private Repository repository;

//    private Config config;

    private String queue;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

//    public Config getConfig() {
//        return config;
//    }
//
//    public void setConfig(Config config) {
//        this.config = config;
//    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    @Override
    public int compareTo(Payload payload) {
        return ComparisonChain.start()
                .compare(this.getRepository(), payload.getRepository(), Ordering.natural().nullsLast())
                .result();
    }
}
