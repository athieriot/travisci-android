package com.github.athieriot.android.travisci.core;

import java.io.Serializable;

public class Build  implements Serializable {

    private String slug;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
