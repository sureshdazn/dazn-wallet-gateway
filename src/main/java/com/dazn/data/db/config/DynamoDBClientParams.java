package com.dazn.data.db.config;

import org.springframework.beans.factory.annotation.Value;

public class DynamoDBClientParams {
	
	@Value("${amazon.dynamodb.connection.timeout}")
	public static int connectionTimeout;
   
	@Value("${amazon.dynamodb.execution.timeout}")
	public static int clientExecutionTimeout;
    
	@Value("${amazon.dynamodb.request.timeout}")
	public static int requestTimeout;
    
	@Value("${amazon.dynamodb.socket.timeout}")
	public static int socketTimeout;
	
	@Value("${amazon.dynamodb.retry.count}")
    public static int maxErrorRetries;
	
}
