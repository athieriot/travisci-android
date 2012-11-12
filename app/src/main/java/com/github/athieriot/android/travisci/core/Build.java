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

    private Date last_build_started_at;

    private Date last_build_finished_at;

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
}
