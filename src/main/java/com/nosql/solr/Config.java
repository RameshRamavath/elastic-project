package com.nosql.solr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Arrays;
import java.util.Optional;


@Configuration
@ConfigurationProperties
public class Config {

    @Value("${solrj.server.zkquorom}")
    private String zkQuorom;

    @Bean(name = "cloudSolrClient")
    public CloudSolrClient cloudSolrClient() {
        logger.info("Zookeeper Quorom: " + zkQuorom);
        Builder builder = new CloudSolrClient.Builder(Arrays.asList(zkQuorom.split(",")), Optional.of("/solr"));
        CloudSolrClient cloudSolrClient = builder.build();
        return cloudSolrClient;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

