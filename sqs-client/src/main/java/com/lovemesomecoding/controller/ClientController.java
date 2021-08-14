package com.lovemesomecoding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lovemesomecoding.model.QueueMessage;
import com.lovemesomecoding.service.QueueSenderService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(value = "demos", produces = "Rest API for demos", tags = "Demo", description = "Demo endpoints")
@Slf4j
@RestController
public class ClientController {
    
    @Autowired
    private QueueSenderService queueSenderService;

    
    @PostMapping("/send-message")
    public ResponseEntity<Boolean> sendMessage(@RequestBody QueueMessage queueMessage) {
        log.info("sendMessage {}", queueMessage.toString());
        return new ResponseEntity<>(queueSenderService.sendQueueMessage(queueMessage), HttpStatus.OK);
    }
}
