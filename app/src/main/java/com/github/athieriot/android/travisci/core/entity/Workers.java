package com.github.athieriot.android.travisci.core.entity;

import org.joda.time.DateTime;

public class Workers {

    private Long id;

    private String name;

    private String host;

    private String state;

    private DateTime last_seen_at;

    private Payload payload;

    private String last_error;

    private class Payload {

        private String type;

//        private Build build;
//
//        private Job job;
//
//        private Repository repository;
//
//        private Config config;
//
//        private String queue;
    }
}

