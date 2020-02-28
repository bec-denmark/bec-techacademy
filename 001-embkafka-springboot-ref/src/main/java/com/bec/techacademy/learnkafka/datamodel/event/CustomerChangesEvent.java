package com.bec.techacademy.learnkafka.datamodel.event;

import com.google.gson.JsonObject;

public class CustomerChangesEvent implements ChangeDataEvent {

    private String subType;
    private String id;
    private String content;
    private String eventId;
    private String acknowledgementId;


    @Override
    public String toString() {
        JsonObject obj = new JsonObject();
        obj.addProperty("type",subType);
        obj.addProperty("id",id);
        obj.addProperty("content",content);
        obj.addProperty("eventId",eventId);
        obj.addProperty("acknowledgementId",acknowledgementId);
        return obj.toString();
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setAcknowledgementId(String acknowledgementId) {
        this.acknowledgementId = acknowledgementId;
    }
}


