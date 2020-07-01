package com.nosql.couchbase;

import javax.validation.constraints.NotNull;

public class RequestModel {

    @NotNull(message = "custId is required")
    private String custId;

    private String custCountry;

    public RequestModel(@NotNull(message = "custId is required") String custId, String custCountry) {
        this.custId = custId;
        this.custCountry = custCountry;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustCountry() {
        return custCountry;
    }

    public void setCustCountry(String custCountry) {
        this.custCountry = custCountry;
    }
}
