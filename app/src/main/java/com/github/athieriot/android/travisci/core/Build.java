package com.github.athieriot.android.travisci.core;

import java.io.Serializable;
import java.util.Date;

public class Build  implements Serializable {

    public static final String SUCCESS = "0";
    public static final String FAILURE = "1";
    private String id;

    private String slug;

    private String description;

    private String last_build_id;

    private String last_build_number;

    private String last_build_status;

    private String last_build_result;

    private Integer last_build_duration;

    private String last_build_language;

    private String last_build_started_at;

    private String last_build_finished_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean isSuccessful() {
        return last_build_status != null && last_build_status.equals(SUCCESS);
    }

    public boolean isFail() {
        return last_build_status != null && last_build_status.equals(FAILURE);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLast_build_id() {
        return last_build_id;
    }

    public void setLast_build_id(String last_build_id) {
        this.last_build_id = last_build_id;
    }

    public String getLast_build_number() {
        return last_build_number;
    }

    public void setLast_build_number(String last_build_number) {
        this.last_build_number = last_build_number;
    }

    public String getLast_build_status() {
        return last_build_status;
    }

    public void setLast_build_status(String last_build_status) {
        this.last_build_status = last_build_status;
    }

    public String getLast_build_result() {
        return last_build_result;
    }

    public void setLast_build_result(String last_build_result) {
        this.last_build_result = last_build_result;
    }

    public Integer getLast_build_duration() {
        return last_build_duration;
    }

    public void setLast_build_duration(Integer last_build_duration) {
        this.last_build_duration = last_build_duration;
    }

    public String getLast_build_language() {
        return last_build_language;
    }

    public void setLast_build_language(String last_build_language) {
        this.last_build_language = last_build_language;
    }

    public String getLast_build_started_at() {
        return last_build_started_at;
    }

    public void setLast_build_started_at(String last_build_started_at) {
        this.last_build_started_at = last_build_started_at;
    }

    public String getLast_build_finished_at() {
        return last_build_finished_at;
    }

    public void setLast_build_finished_at(String last_build_finished_at) {
        this.last_build_finished_at = last_build_finished_at;
    }
}
