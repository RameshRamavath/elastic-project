package com.nosql.hbase.develop;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RepoImpl implements Repo {


    protected static final int MAX_VERSIONS = 10;
    public static final String PIPE = "|";
    private static String CF = "cf";
    private static byte[] CF_BYTES = Bytes.toBytes(CF);


    @Autowired
    private Connection connection;

    @Override
    public List<String> getTables() {

        List<String> tables_ = new ArrayList<String>();

        try {
            TableName[] tables = connection.getAdmin().listTableNames();
            Arrays.asList(tables).forEach(tableName -> {
                tables_.add(tableName.getNameAsString());
            });
        } catch (IOException e) {
            System.out.println("Error -> " + e.getMessage());
        }
        return tables_;
    }


    @Override
    public Map<String, OutputRecord> getRecord(String tableName, String rowKey) {

        Map<String, OutputRecord> recordMap = new HashMap<>();
        Table table;
        Get get;
        Result result = null;
        try
        {
            table = connection.getTable(TableName.valueOf(tableName));
            get = new Get(Bytes.toBytes(rowKey));
            result = table.get(get);
        } catch (IOException e) {
            System.out.println("" + e.getMessage());
        }

        OutputRecord record = new OutputRecord();

        if (result != null) {
            Arrays.stream(result.rawCells()).forEach(cell -> {
                record.put(new String(CellUtil.cloneQualifier(cell)), new String(CellUtil.cloneValue(cell)));
            });
            recordMap.put(rowKey, record);
        } else
            recordMap.put(rowKey + " is not present in the table " + tableName, record);
        return recordMap;
    }

    @Override
    public Map<String, OutputRecord> getRecords(String tableName,List<String> rowKeys) throws IOException {

        Table table = connection.getTable(TableName.valueOf(tableName));

        Map<String, OutputRecord> response = new HashMap<>();

        List<Get> gets = new ArrayList<>();

        rowKeys.parallelStream().forEach(rowkey -> {
            Get get = new Get(Bytes.toBytes(rowkey));
            gets.add(get);
        });

        long startTime = System.currentTimeMillis();

        Result[] results = new Result[rowKeys.size()];
        try {
            results = table.get(gets);
        } catch (IOException e) {
            System.out.println("Issue Retrieving - " + e.getMessage());
        }

        if (results.length > 0) {
            Arrays.asList(results).parallelStream().forEach(rslt -> {
                String key = "";
                OutputRecord record = new OutputRecord();
                key = Bytes.toString(rslt.getRow());
                if(key != "") {
                    Arrays.asList(rslt.rawCells()).stream().forEach(cell -> {
                        record.put(new String(CellUtil.cloneQualifier(cell)), new String(CellUtil.cloneValue(cell)));
                    });
                    response.put(key, record);
                }
            });
            long endTime = System.currentTimeMillis();
            System.out.println("Time to retrieve -> " + rowKeys.size() + " = " + (endTime - startTime));
        }

        return response;
    }

    @Override

    public Map<String, List<OutputRecord>> scanByRowPrefix (String prefix, String tableName, List<String> fields) throws IOException{

        Map<String, List<OutputRecord>> mapOfRecords = new HashMap<>();
        Scan scan = new Scan();
        scan.addFamily(CF_BYTES);
        scan.setRowPrefixFilter(Bytes.toBytes(prefix));

        if (!fields.isEmpty()) {
            fields.stream().forEach(field -> {
                scan.addColumn(CF_BYTES, Bytes.toBytes(field));
            });
        }

        ResultScanner resultScanner = null;
        try {
            Table processableTable = connection.getTable(TableName.valueOf(tableName));
            resultScanner = processableTable.getScanner(scan);
        } catch (IOException e) {
            System.out.println("" + e.getMessage());
        }

        List<OutputRecord> records = new LinkedList<>();

        if (resultScanner != null) {
            for(Result result: resultScanner){
                OutputRecord record = new OutputRecord();
                for (Cell cell : result.listCells()) {
                    String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    // System.out.printf("Qualifier : %s : Value : %s", qualifier, value);
                    record.put(qualifier,value);
                }
                records.add(record);
            }
            mapOfRecords.put(prefix,records);
        } else {
            mapOfRecords.put(prefix + " is not present in the table " + tableName, records);
        }
        return mapOfRecords;
    }
}
