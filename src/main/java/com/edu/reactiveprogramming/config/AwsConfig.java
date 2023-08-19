package com.edu.reactiveprogramming.config;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AwsConfig {

	@Bean
	@Primary
	public AwsCredentialsProvider getCredentialProvider() {
		return StaticCredentialsProvider.create(AwsBasicCredentials.create("test", "test"));
	}
	
	@Bean
	public SqsAsyncClient sqsAsyncClient(AwsCredentialsProvider awsCredentialsProvider) {
		return SqsAsyncClient.builder()
				.region(Region.of("us-east-1"))
				.credentialsProvider(awsCredentialsProvider)
				.endpointOverride(URI.create("http://localhost:4566"))
				.build();
	}

}
