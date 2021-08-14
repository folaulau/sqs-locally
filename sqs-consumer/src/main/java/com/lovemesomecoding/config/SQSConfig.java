package com.lovemesomecoding.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Configuration
public class SQSConfig {
    

    @Value("${aws.deploy.region:us-west-2}")
    private String targetRegion;
    
    private Regions getTargetRegion() {
        return Regions.fromName(targetRegion);
    }

    @Bean
    public AmazonSQS amazonSQS() {
        String serviceEndpoint = "http://localhost:4566";// "sqs." + getTargetRegion().getName() + ".amazonaws.com";
        String signinRegion = getTargetRegion().getName();
        String secretKey = "testsecretKey";
        String accessKey = "testaccessKey";
        //@formatter:off
        return AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey)))
                .withEndpointConfiguration(new EndpointConfiguration(serviceEndpoint, signinRegion))
                .build();
        //@formatter:on
    }
}
