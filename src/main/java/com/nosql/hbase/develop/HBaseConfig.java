package com.nosql.hbase.develop;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import java.io.IOException;


public class HBaseConfig {


    @Value("${hbase.zookeeper.quorom}")
    private String zkQuorum;

    @Value("${hbase.zookeeper.port}")
    private String zkPort;

    @Value("${hbase.zookeeper.parent}")
    private String zkParent;

    @Value("${hbase.master.principle}")
    private String hbaseMasterUser;

    @Value("${hbase.regionserver.principle}")
    private String hbaseRegionServerUser;

    @Value("${hadoop.security.configuration}")
    private String securityConf;

    @Value("${hdfs.keytabUser}")
    private String keytabUser;

    @Value("${hdfs.keytabLoc}")
    private String keytabLoc;

    @Autowired
    private org.apache.hadoop.conf.Configuration hbaseConfiguration;

    @Bean(name = "hbaseConfiguration")
    public org.apache.hadoop.conf.Configuration hbaseConfiguration() {

        org.apache.hadoop.conf.Configuration configuration = null;
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", zkQuorum);
        configuration.set("hbase.zookeeper.property.clientPort", zkPort);
        configuration.set("zookeeper.znode.parent", zkParent);

        configuration.set("hbase.master.kerberos.principal", hbaseMasterUser);
        configuration.set("hbase.regionserver.kerberos.principal", hbaseRegionServerUser);
        configuration.set("hadoop.kerberos.keytab.login.autorenewal.enabled", "true");
        configuration.set("hadoop.security.authentication", securityConf);
        configuration.set("hbase.security.authentication", securityConf);

        UserGroupInformation.setConfiguration(configuration);
        try {
            UserGroupInformation.loginUserFromKeytab(keytabUser, keytabLoc);
        } catch (IOException e) {
            System.out.println("Exception occured during connecting with kerberos security -> " + e.getMessage());
        }

        return configuration;

    }

    @Bean(name = "connection")
    public Connection connection() throws IOException {
        Connection connection = ConnectionFactory.createConnection(hbaseConfiguration);
        return connection;
    }
}
