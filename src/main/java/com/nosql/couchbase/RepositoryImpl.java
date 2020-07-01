package com.wu.service.agent.repository;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.ParameterizedN1qlQuery;
import com.nosql.couchbase.RequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class RepositoryImpl{

    private String lookup;

    @Autowired
    private Bucket bucket;

    @Value("${bucket.name}")
    private String bucketName;

    @PostConstruct

    public void init() {

        lookup = "SELECT * FROM " + bucketName + " WHERE tid = $tid and customer.custId  = $custid AND " +
                "customer.CounterID  = $CounterID AND (ANY cntry IN customer.PayoutCountry SATISFIES cntry=$PayoutCountry END) and (ANY cur IN customer.PayoutCurrency SATISFIES cur=$PayoutCurrency END)";

    }

    public N1qlQueryResult getObject (RequestModel model) {

        JsonObject values = JsonObject.create()
                .put("custid", model.getCustId())
                .put("custCountry",model.getCustCountry());


        ParameterizedN1qlQuery q = N1qlQuery.parameterized(lookup, values);
        return bucket.query(q);

    }

}
