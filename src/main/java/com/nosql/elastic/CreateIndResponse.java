package com.nosql.elastic;

public class CreateIndResponse {
    private boolean acknowledged;
    private boolean shards_acknowledged;
    private String index;

    public CreateIndResponse() {
        super();
    }

    public CreateIndResponse(boolean acknowledged, boolean shards_acknowledged, String index) {
        this.acknowledged = acknowledged;
        this.shards_acknowledged = shards_acknowledged;
        this.index = index;
    }

    public boolean getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public boolean getShards_acknowledged() {
        return shards_acknowledged;
    }

    public void setShards_acknowledged(boolean shards_acknowledged) {
        this.shards_acknowledged = shards_acknowledged;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
