package com.github.athieriot.android.travisci.core.entity;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Config {

    private List<String> before_install;

    private List<String> install;

    private List<String> script;

    private String rvm;

    private List<String> env;

    private Notifications notifications;

    private String bundler_args;
//
//    "matrix": {
//        "allow_failures": [{
//            "rvm": "2.0.0"
//        }]
//    },

    public List<String> getScript() {
        return script;
    }

    public void setScript(List<String> script) {
        this.script = script;
    }

    public List<String> getInstall() {
        return install;
    }

    public void setInstall(List<String> install) {
        this.install = install;
    }

    public List<String> getBefore_install() {
        return before_install;
    }

    public void setBefore_install(List<String> before_install) {
        this.before_install = before_install;
    }

    public String getRvm() {
        return rvm;
    }

    public void setRvm(String rvm) {
        this.rvm = rvm;
    }

    public List<String> getEnv() {
        return env;
    }

    public void setEnv(List<String> env) {
        this.env = env;
    }

    public Notifications getNotifications() {
        return notifications;
    }

    public void setNotifications(Notifications notifications) {
        this.notifications = notifications;
    }

    public String getBundler_args() {
        return bundler_args;
    }

    public void setBundler_args(String bundler_args) {
        this.bundler_args = bundler_args;
    }
}