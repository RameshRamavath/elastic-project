package com.nosql.solr;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;


@Component
@org.springframework.stereotype.Controller

public class Controller {

    private static String CONSTANT_URI = "start=0&rows=10000&omitHeader=true&q=*:*&";

    public SolrjService solrService;

    @GetMapping("/query/{collection}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryCollection(@PathParam("collection") String collection, @Context UriInfo uriInfo) {

        String uriQuery = uriInfo.getRequestUri().getQuery();
        String query = CONSTANT_URI + uriQuery;

        try {
            return Response.status(Response.Status.OK).entity(solrService.queryCollection(collection, query)).build();
        } catch (SolrServerException | IOException e) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("Error with Solr").build();
        }
    }
}
