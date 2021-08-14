package com.lovemesomecoding.service;

import com.lovemesomecoding.model.QueueMessage;

public interface QueueSenderService {

    boolean sendQueueMessage(QueueMessage queueMessage);
}
