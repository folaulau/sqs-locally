package com.lovemesomecoding.sqs;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.lovemesomecoding.model.QueueMessage;
import com.lovemesomecoding.utils.ObjectMapperUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SQSListener {

    @JmsListener(destination = "mailer2.fifo")
    public void listen(String jsonMessage) {
        log.info("listen jsonMessage={}", jsonMessage);
        
        QueueMessage queueMessage = ObjectMapperUtils.fromJsonString(jsonMessage, QueueMessage.class);

        log.info("title={}", queueMessage.getTitle());
    }
}
