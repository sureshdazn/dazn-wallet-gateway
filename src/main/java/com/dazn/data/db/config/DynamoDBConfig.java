package com.dazn.data.db.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.retry.PredefinedRetryPolicies;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class DynamoDBConfig {
	
	@Value("${amazon.dynamodb.endpoint}")
	private String amazonDynamoDBEndpoint;
	
	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;
	
	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;
	
	@Value("${amazon.aws.region}")
	private String amazonAWSRegion;

    @Bean
    DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }
    
    private AmazonDynamoDB buildAmazonDynamoDB() {
    	
    	return AmazonDynamoDBClientBuilder.standard()
               .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonAWSRegion))
               .withCredentials(new AWSStaticCredentialsProvider( new BasicAWSCredentials( amazonAWSAccessKey, amazonAWSSecretKey)))
               .withClientConfiguration(createDynamoDBClientConfiguration())
               .build();
         
    }
    
    /* https://aws.amazon.com/blogs/database/tuning-aws-java-sdk-http-request-settings-for-latency-aware-amazon-dynamodb-applications/
     * 
     * configured dynamo-db client properties by referring above documentation
     * 
     */
    
    private static ClientConfiguration createDynamoDBClientConfiguration() {
    	
    	return new ClientConfiguration().withConnectionTimeout(DynamoDBClientParams.connectionTimeout)
                						.withClientExecutionTimeout(DynamoDBClientParams.clientExecutionTimeout)
                						.withRequestTimeout(DynamoDBClientParams.requestTimeout)
                						.withSocketTimeout(DynamoDBClientParams.socketTimeout)
                						.withRetryPolicy(PredefinedRetryPolicies.getDynamoDBDefaultRetryPolicyWithCustomMaxRetries(
                										 DynamoDBClientParams.maxErrorRetries));
        
    }

    
}


