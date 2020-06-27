package com.nosql.elastic;

public class ErrorDetails {
    private String field;
    private String advice;

    public ErrorDetails(String field, String advice) {
        this.field = field;
        this.advice = advice;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }
}
