package com.nosql.couchbase;

import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.N1qlQueryRow;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosql.couchbase.RequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/age")

public class Controller {


    @Value("${bucket.name}")
    private String bucketName;
    private static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private RepositoryImpl repository;

    @Autowired
    public Controller(RepositoryImpl repository) {
        this.repository = repository;
    }


    @PostMapping(value = "/payout", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)

    public @ResponseBody ResponseEntity<Object> lookup (@Valid @RequestBody RequestModel reqObj, BindingResult bindingResult)
            throws Exception{

        if (bindingResult.hasErrors()) {
            System.out.println("Lookup Failed: Invalid Input request");
            throw NotFoundEx;
        }

        ReponseModel finalResponse = null;
        long startTs = System.currentTimeMillis();

        N1qlQueryResult queryResult = repository.getObject(reqObj);

        if(queryResult.info().resultCount()==0){
            logger.info("no records found");
            throw ResourceNotfoundEx();
        } else {
            for (N1qlQueryRow row : queryResult) {
                ReponseModel payOut = objectMapper.readValue(row.value().get(bucketName).toString(), ReponseModel.class);
                finalResponse = payOut;
            }
        }
        logger.info("Request to get " + reqObj + " completed and took " + (System.currentTimeMillis() - startTs) + " ms");
        return new ResponseEntity<>(finalResponse, HttpStatus.OK);
    }

}
