package com.lovemesomecoding.service;

import com.lovemesomecoding.model.QueueMessage;

public interface QueueReceiverService {

    boolean sendQueueMessage(QueueMessage queueMessage);
}
