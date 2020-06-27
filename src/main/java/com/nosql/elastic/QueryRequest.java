package com.nosql.elastic;

import javax.validation.constraints.NotEmpty;

public class QueryRequest {
    @NotEmpty(message = "Please provide valid query...!")
    private String query;
    @NotEmpty (message = "Please provide exiting collection name...!")
    private String collection;
    private String [] includeFields;
    private String [] excludeFields;

    public QueryRequest(String query, String collection, String [] includeFields, String [] excludeFields) {
        this.query = query;
        this.collection = collection;
        this.includeFields = includeFields;
        this.excludeFields = excludeFields;
    }

    public QueryRequest() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String [] getIncludeFields() {
        return includeFields;
    }

    public void setIncludeFields(String [] includeFields) {
        this.includeFields = includeFields;
    }
    public String[] getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeFields(String[] excludeFields) {
        this.excludeFields = excludeFields;
    }
}
