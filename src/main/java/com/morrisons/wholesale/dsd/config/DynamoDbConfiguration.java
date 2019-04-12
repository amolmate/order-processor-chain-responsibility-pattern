package com.morrisons.wholesale.dsd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Configuration
public class DynamoDbConfiguration {

	private String dynamoOrderHeaderTable;

	private String dynamoOrderItemTable;

	private int batchLimit;

	@Bean("amazonDynamoDb")
	public AmazonDynamoDB getAmazonDynamoDBClient() {
		
		return AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_WEST_1)
				.withCredentials(new DefaultAWSCredentialsProviderChain())
				.withClientConfiguration(new ClientConfiguration()).build();
	}
}