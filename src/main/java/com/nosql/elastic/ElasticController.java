package com.nosql.elastic;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/transaction-search/v1")

public class ElasticController {


    private Repo repo;

    @Autowired
    public ElasticController(Repo repo) {
        this.repo = repo;
    }

    @GetMapping("/health")
    public ClusterHealthResponse getClusterHealth() throws IOException {
        return repo.checkClusterHealth();
    }

    @GetMapping("/query/{index}/{id}")
    public ResponseEntity<Object> getDocById(@PathVariable String index, @PathVariable String id) throws IOException {
        Object result = repo.queryByID(index, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/query")
    public ResponseEntity<Object> getQueryResults1(@Valid @RequestBody QueryRequest request) throws IOException {
        List<Object> result = repo.queryIndex(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/index")
    public ResponseEntity<CreateIndResponse> createIndex(@RequestBody CreateIndexSchema indexProps) throws IOException {
        CreateIndResponse response = repo.createIndex(indexProps);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/index")
    public ResponseEntity<String> updateIndex(@RequestBody UpdateSchemaRequest updateRequestBody) throws IOException {
        String response = repo.updateIndex(updateRequestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/mapping/{indexName}")
    public ResponseEntity<Object> getMapping(@PathVariable String indexName) throws IOException {
        Object response = repo.getMapping(indexName);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
