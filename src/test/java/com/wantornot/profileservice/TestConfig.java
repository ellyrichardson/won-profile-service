package com.wantornot.profileservice;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.wantornot.profileservice.dataaccesslayer.ProfileDALImpl;
import com.wantornot.profileservice.utility.ZonedDateTimeReadConverter;
import com.wantornot.profileservice.utility.ZonedDateTimeWriteConverter;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.exceptions.DistributionException;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
@EnableMongoRepositories("com.wantornot.profileservice")
@ComponentScan("com.wantornot.profileservice")
public class TestConfig {
	private static final String CONNECTION_STRING = "mongodb://%s:%d";

	private String ip = "localhost";
	
	private int port = 27020;
	
	@Autowired
	MongoConfig mongoConfig;
	
	@Bean(destroyMethod = "stop")
    public MongodExecutable mongodExecutable() throws DistributionException, UnknownHostException, IOException {
    	MongodStarter starter = MongodStarter.getDefaultInstance();
        return starter.prepare(mongodConfig());
    }
    
    @Bean
    public MongoClient mongodClient() {
    	return MongoClients.create(String.format(CONNECTION_STRING, ip, port));
    }
    
    @Bean 
    public IMongodConfig mongodConfig() throws UnknownHostException, IOException {
    	return new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();
    }
    
    @Bean
    public MongoTemplate mongoTemplate() {
    	MongoTemplate mongoTemplate = new MongoTemplate(mongodClient(), "profile");
    	MappingMongoConverter mongoMapping = (MappingMongoConverter) mongoTemplate.getConverter();
        mongoMapping.setCustomConversions(mongoConfig.customConversions()); // tell mongodb to use the custom converters
        mongoMapping.afterPropertiesSet();
    	return mongoTemplate;
    }
    
    @Bean
    @Primary
    public ProfileDALImpl testProfileDALImpl() throws IOException {
    	return new ProfileDALImpl(mongoTemplate());
    }
}
