package com.nosql.couchbase;


import com.couchbase.client.core.env.QueryServiceConfig;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties

public class Config {

    @Value("${couchbase.server.address}")
    private String couchbaseServerAddress;

    @Value("${couchbase.server.port}")
    private String couchbaseServerPort;

    @Value("${couchbase.server.user:#{null}}")
    private String couchbaseUser;

    @Value("${couchbase.server.password:#{null}}")
    private String couchbasePassword;

    @Value("${bucket.name}")
    private String bucket;

    @Value("${bucket.password:#{null}}")
    private String bucketPassword;

    @Bean(name="couchbaseEnvironment")
    public CouchbaseEnvironment getEnv(){
        return  DefaultCouchbaseEnvironment
                .builder()
                .connectTimeout(10000)
                .kvTimeout(500)
                .queryServiceConfig(QueryServiceConfig.create(2, 5))
                .build();
    }

    @Bean(name="couchbaseCluster")
    public CouchbaseCluster getCluster(CouchbaseEnvironment couchbaseEnvironment){
        CouchbaseCluster cluster = CouchbaseCluster.create(couchbaseEnvironment, couchbaseServerAddress);
        if(couchbaseUser!=null && couchbaseUser!="")
            cluster.authenticate(couchbaseUser, couchbasePassword);

        return cluster;
    }

    @Bean(name = "bucket")
    public Bucket getBucket(CouchbaseCluster couchbaseCluster) {

        if(bucketPassword!=null && bucketPassword!="")
            return couchbaseCluster.openBucket(bucket, bucketPassword);
        else
            return couchbaseCluster.openBucket(bucket);
    }
}
