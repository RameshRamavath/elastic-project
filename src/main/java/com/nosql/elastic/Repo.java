package com.nosql.elastic;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;

import java.io.IOException;
import java.util.List;

public interface Repo {

    ClusterHealthResponse checkClusterHealth() throws IOException;

    CreateIndResponse createIndex(CreateIndexSchema indexProperties) throws IOException;

    String updateIndex (UpdateSchemaRequest updateRequestBody) throws IOException;

    Object getMapping (String indexName) throws IOException;

    List<Object> queryIndex(QueryRequest request) throws IOException;

    Object queryByID(String index, String id) throws IOException;
}
