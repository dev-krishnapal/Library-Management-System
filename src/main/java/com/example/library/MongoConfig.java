package com.example.library;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "library_db";
    }

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(
                "mongodb+srv://krishnapalpanwar97_db_user:wvIG21pBHivYFY3r@kpcluster.5sdsroj.mongodb.net/?appName=KpCluster");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }
}
