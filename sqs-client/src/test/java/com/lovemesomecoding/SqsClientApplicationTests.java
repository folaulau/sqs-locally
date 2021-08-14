package com.lovemesomecoding;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.amazonaws.services.sqs.model.SendMessageResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class SqsClientApplicationTests {

    @Autowired
    private AmazonSQS sqs;

    String            sqsHost = "http://localhost:4566/000000000000";

    @Test
    void createQueue() {

        CreateQueueResult result = sqs.createQueue("mailer");

        log.info(result.getQueueUrl());

        log.info(result.getSdkResponseMetadata().getRequestId());
    }

    @Test
    void createFIFOQueue() {

        CreateQueueResult result = sqs.createQueue("mailer2.fifo");

        log.info(result.getQueueUrl());

        log.info(result.getSdkResponseMetadata().getRequestId());
    }

    @Test
    void listQueue() {

        ListQueuesResult result = sqs.listQueues();
        log.info("urls={}", result.getQueueUrls());
        for (String queueUrl : result.getQueueUrls()) {
            log.info("queueUrl={}", queueUrl);
        }
    }

    @Test
    void sendMessageToQueue() {

        SendMessageResult result = sqs.sendMessage(sqsHost + "/mailer", "test message");
        log.info("msgId={}", result.getMessageId());

    }

    @Test
    void receiveMessageFromQueue() {

        ReceiveMessageResult result = sqs.receiveMessage(sqsHost + "/mailer2.fifo");
        log.info("RequestId={}", result.getSdkResponseMetadata().getRequestId());
        for (Message msg : result.getMessages()) {
            log.info("msgId={}", msg.getMessageId());
            log.info("body={}", msg.getBody());

            msg.setReceiptHandle(UUID.randomUUID().toString());
        }
    }

}
