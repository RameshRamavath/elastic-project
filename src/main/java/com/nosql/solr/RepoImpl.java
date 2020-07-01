package com.nosql.solr;

import com.google.gson.Gson;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.solr.client.solrj.SolrQuery;

@Service

public class RepoImpl {


    @Autowired
    public CloudSolrClient cloudSolrClient;

    public SolrQuery prepareQuery(String filterQuery) {

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setRequestHandler("/select");

        String[] listOf;

        listOf = filterQuery.split("&");
        for (int i = 0; i < listOf.length; i++) {

            String[] tmp = listOf[i].split("=");
            if (tmp[0].equals("start") || tmp[0].equals("rows") || tmp[0].equals("group.limit")) {
                solrQuery.set(tmp[0], tmp[1]);
            } else if (tmp[0].equals("group") || tmp[0].equals("omitHeader")) {
                solrQuery.set(tmp[0], tmp[1]);
            } else if (tmp[0].equals("group.field") || tmp[0].equals("fl")) {
                solrQuery.set(tmp[0], tmp[1]);
            } else if (tmp[0].equals("fq")) {
                solrQuery.addFilterQuery(tmp[1]);
            } else if (tmp[0].equals("q")) {
                solrQuery.set(tmp[0], tmp[1]);
            }
        }
        return solrQuery;
    }

    public String queryCollection(String collection, String filterQuery) throws SolrServerException, IOException {


        cloudSolrClient.setDefaultCollection(collection);
        SolrQuery solrQuery = prepareQuery(filterQuery);
        SolrDocumentList solrDocList = new SolrDocumentList();
        SolrDocumentList solrResponse = cloudSolrClient.query(solrQuery).getResults();

        solrResponse.forEach(solrDoc -> {
            Map<String, Object> tempMap = new HashMap<>();
            for (String key : solrDoc.keySet()) {
                Object v = null;
                try {
                    v = cast(solrDoc.get(key)).get(0);
                } catch (Exception ex) {
                    v = solrDoc.get(key);
                }
                tempMap.put(key, v);
            }
            solrDocList.add(new SolrDocument(tempMap));
        });
        Gson gson = new Gson();
        return gson.toJson(solrDocList);

    }
}
