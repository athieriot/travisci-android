package com.github.athieriot.android.travisci.core.entity;

import java.io.Serializable;

public class Notifications implements Serializable {

    private boolean email;

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    //    "irc": {
//        "on_success": "change",
//                "on_failure": "always",
//                "channels": [
//        "irc.freenode.org#rails-contrib"
//        ]
//    },
//            "campfire": {
//        "on_success": "change",
//                "on_failure": "always",
//                "rooms": [
//        {
//            "secure": "CGWvthGkBKNnTnk9YSmf9AXKoiRI33fCl5D3jU4nx3cOPu6kv2R9nMjt9EAo\nOuS4Q85qNSf4VNQ2cUPNiNYSWQ+XiTfivKvDUw/QW9r1FejYyeWarMsSBWA+\n0fADjF1M2dkDIVLgYPfwoXEv7l+j654F1KLKB69F0F/netwP9CQ="
//        }
//        ]
//    }
}
