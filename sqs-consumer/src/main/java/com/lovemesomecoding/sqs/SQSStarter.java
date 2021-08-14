package com.lovemesomecoding.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SQSStarter {
	
	@Autowired
	private SQSConsumer sqsListener;
	
	@PostConstruct
	public void init() {
		sqsListener.processQueue();
	}
}
