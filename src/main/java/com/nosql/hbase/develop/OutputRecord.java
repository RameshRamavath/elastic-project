package com.nosql.hbase.develop;

import java.util.HashMap;
import java.util.Map;

public class OutputRecord extends HashMap<String, String> {

    private static final long serialVersionUID = 1L;

    private Map<String, String> fieldMap;

    public Map<String, String> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }
}
