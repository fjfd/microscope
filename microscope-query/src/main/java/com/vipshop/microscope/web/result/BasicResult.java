package com.vipshop.microscope.web.result;

import org.codehaus.jackson.annotate.JsonIgnore;


public class BasicResult {

    @JsonIgnore
    protected String callback;

    @JsonIgnore
    public String getCallback() {
        return callback;
    }

    @JsonIgnore
    public void setCallback(String callback) {
        this.callback = callback;
    }
    
}
