package com.nosql.elastic;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class CreateIndexSchema {
    @NotNull
    @NotEmpty
    private String indexName;
    @NotNull @NotEmpty
    private int numberOfShards;
    @NotNull @NotEmpty
    private int numberOfReplicas;
    @NotNull @NotEmpty
    private boolean dynamic;

    @NotNull @NotEmpty
    private Map<String, Object> properties;

    public CreateIndexSchema() {
        super();
    }

    public CreateIndexSchema(String indexName, int numberOfShards, int numberOfReplicas,boolean dynamic, Map<String, Object> properties) {
        this.indexName = indexName;
        this.numberOfShards = numberOfShards;
        this.numberOfReplicas = numberOfReplicas;
        this.properties = properties;
        this.dynamic =dynamic;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }

    public int getNumberOfShards() {
        return numberOfShards;
    }

    public void setNumberOfShards(int numberOfShards) {
        this.numberOfShards = numberOfShards;
    }

    public int getNumberOfReplicas() {
        return numberOfReplicas;
    }

    public void setNumberOfReplicas(int numberOfReplicas) {
        this.numberOfReplicas = numberOfReplicas;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
