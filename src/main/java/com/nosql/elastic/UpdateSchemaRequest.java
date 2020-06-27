package com.nosql.elastic;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

public class UpdateSchemaRequest {
    @NotNull
    @NotEmpty
    private String indexName;

    @NotNull
    @NotEmpty
    private Map<String, Object> properties;

    public UpdateSchemaRequest(@NotNull @NotEmpty String indexName, @NotNull @NotEmpty Map<String, Object> properties) {
        this.indexName = indexName;
        this.properties = properties;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
