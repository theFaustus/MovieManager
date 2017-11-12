package com.isa.pad.moviemanager.client;

/**
 * Created by Faust on 11/12/2017.
 */
public enum ResponseType {
    JSON_TYPE("json"), XML_TYPE("xml");

    private String type;

    ResponseType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
