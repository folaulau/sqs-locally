package com.lovemesomecoding.sqs;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.lovemesomecoding.model.QueueMessage;
import com.lovemesomecoding.utils.ObjectMapperUtils;

@Service
public class SQSConsumer {

    private final Logger log   = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmazonSQS    amazonSQS;

    private String       queue = "mailer2.fifo";

    public void processQueue() {
        log.info("processQueue, url={} ", queue);
        int waitingSeconds = 20;

        String queueUrl = amazonSQS.getQueueUrl(queue).getQueueUrl();

        while (true) {
            try {
                final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueUrl);
                receiveMessageRequest.withWaitTimeSeconds(waitingSeconds);
                receiveMessageRequest.setMaxNumberOfMessages(10);
                final List<Message> messages = amazonSQS.receiveMessage(receiveMessageRequest).getMessages();

                for (Message msg : messages) {
                    processMessage(queue, msg);

                    DeleteMessageResult deleteMessageResult = amazonSQS.deleteMessage(new DeleteMessageRequest(queueUrl, msg.getReceiptHandle()));

                    log.info("DeleteMessageResult={}", deleteMessageResult.getSdkResponseMetadata().getRequestId());
                }

            } catch (Exception e) {
                log.error("SQS is failing {}", e.getLocalizedMessage());

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    log.warn("ignore");
                }
            }
        }
    }

    private void processMessage(String queueUrl, final Message message) {
        int retryCount = 0;
        // retry policy
        while (true) {
            try {
                log.info("queueUrl={}", queueUrl);
                log.info("body={}", message.getBody());
                consumeMsg(message.getBody());
                break;
            } catch (Exception e) {
                if (retryCount == 3) {
                    break;
                }
                log.warn("retryCount={}", retryCount);
                retryCount++;
            }
        }
    }

    private void consumeMsg(String jsonMessage) {
        QueueMessage queueMessage = ObjectMapperUtils.fromJsonString(jsonMessage, QueueMessage.class);

        log.info("title={}", queueMessage.getTitle());
    }
}
