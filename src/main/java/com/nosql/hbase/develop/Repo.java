package com.nosql.hbase.develop;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Repo {

    List<String> getTables();
    Map<String, OutputRecord> getRecords(String tableName, List<String> rowKeys) throws InterruptedException, IOException;
    Map<String, OutputRecord> getRecord(String tableName, String rowKey);
    Map<String, List<OutputRecord>> scanByRowPrefix(String prefix, String tableName, List<String> fields) throws IOException;

}
