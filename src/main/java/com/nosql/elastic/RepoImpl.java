package com.nosql.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class RepoImpl implements Repo {


    private RestHighLevelClient client;

    private ObjectMapper objectMapper;

    @Autowired
    public RepoImpl(RestHighLevelClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }


    @Override
    public ClusterHealthResponse checkClusterHealth() throws IOException {
        ClusterHealthRequest request = new ClusterHealthRequest();
        request.level(ClusterHealthRequest.Level.SHARDS);
        ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
        return response;
    }

    @Override
    public CreateIndResponse createIndex(CreateIndexSchema indexProperties) throws IOException {

        CreateIndexSchema props = indexProperties;
        CreateIndexRequest request = new CreateIndexRequest(props.getIndexName());
        request.settings(Settings.builder()
                .put("index.number_of_shards", props.getNumberOfShards())
                .put("index.number_of_replicas", props.getNumberOfReplicas())
        );

        Map<String, Object> schemaMap = new HashMap<>();
        schemaMap.put("properties", props.getProperties());
        schemaMap.put("dynamic", props.isDynamic());
        request.mapping(props.getIndexName(), schemaMap);
        CreateIndexResponse createResponse = client.indices().create(request, RequestOptions.DEFAULT);
        CreateIndResponse response = new CreateIndResponse();
        response.setAcknowledged(createResponse.isAcknowledged());
        response.setShards_acknowledged(createResponse.isShardsAcknowledged());
        response.setIndex(createResponse.index());
        return response;
    }

    @Override
    public String updateIndex(UpdateSchemaRequest updateRequestBody) throws IOException {

        PutMappingRequest request = new PutMappingRequest(updateRequestBody.getIndexName());
        request.type(updateRequestBody.getIndexName());
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("properties", updateRequestBody.getProperties());
        request.source(jsonMap);
        AcknowledgedResponse updateResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
        return updateResponse.isAcknowledged() ? "Mapping Updated Successfully...!" : "Mapping Update Failed ...!!";
    }

    @Override
    public  Object getMapping (String indexName) throws IOException {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(indexName);
        GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
        return response.getMappings().get(indexName).get(indexName).getSourceAsMap();
    }

    @Override
    public List<Object> queryIndex(QueryRequest request) throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(request.getCollection());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(request.getIncludeFields(), request.getExcludeFields());
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(request.getQuery());
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        return getSearchResult(searchResponse);
    }

    @Override
    public Object queryByID(String index, String mtcn) throws IOException {
        GetRequest getRequest = new GetRequest(index, mtcn);
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        if (getResponse.getSourceAsString().isEmpty()) {
            throw new RecordNotFound(BAD_REQUEST, "No matching docs found, try another query...!");
        } else {
            return new ObjectMapper().readValue(getResponse.getSourceAsString(), Object.class);
        }
    }

    private List<Object> getSearchResult(SearchResponse response) {

        SearchHit[] searchHit = response.getHits().getHits();
        List<Object> resultDocuments = new ArrayList<>();
        if (searchHit.length == 0) {
            throw new RecordNotFound(BAD_REQUEST, "No matching docs found, try another query...!");
        } else {
            for (SearchHit hit : searchHit) {
                resultDocuments.add(objectMapper.convertValue(hit.getSourceAsMap(), Object.class));
            }
        }

        return resultDocuments;
    }

    private SearchRequest buildLookupRequest(String index) {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        return searchRequest;
    }
}
