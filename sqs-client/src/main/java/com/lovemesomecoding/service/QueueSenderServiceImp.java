package com.lovemesomecoding.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.lovemesomecoding.model.QueueMessage;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QueueSenderServiceImp implements QueueSenderService {
    
    @Autowired
    private AmazonSQS sqs;
    
    String queueUrl = "http://localhost:4566/000000000000/mailer2.fifo";
    
    @Override
    public boolean sendQueueMessage(QueueMessage queueMessage) {
        queueMessage.setUuid(UUID.randomUUID().toString());
        queueMessage.setNow(LocalDateTime.now());
        
        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(queueMessage.toString())
                .withMessageDeduplicationId(UUID.randomUUID().toString());
        
        sqs.sendMessage(send_msg_request);
        
        return true;
    }

}
