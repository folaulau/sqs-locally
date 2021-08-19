package com.lovemesomecoding.config;

import javax.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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
        return amazonSQSClientBuilder().build();
    }

    private AmazonSQSClientBuilder amazonSQSClientBuilder() {
        String serviceEndpoint = "http://localhost:4566";// "sqs." + getTargetRegion().getName() + ".amazonaws.com";
        String signinRegion = getTargetRegion().getName();
        String secretKey = "testsecretKey";
        String accessKey = "testaccessKey";
        //@formatter:off
        return AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey,secretKey)))
                .withEndpointConfiguration(new EndpointConfiguration(serviceEndpoint, signinRegion));
        //@formatter:on
    }
    
    @Bean
    public SQSConnectionFactory sqsConnectionFactory() {
        return new SQSConnectionFactory(new ProviderConfiguration(), amazonSQSClientBuilder());
    }

    /**
     * just for listener
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(sqsConnectionFactory());
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("3-10");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }
    
    @Bean
    public JmsTemplate defaultJmsTemplate() {
        return new JmsTemplate(sqsConnectionFactory());
    }
}
