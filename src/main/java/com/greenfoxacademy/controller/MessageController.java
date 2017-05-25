package com.greenfoxacademy.controller;

import com.greenfoxacademy.model.OutputMessage;
import com.greenfoxacademy.model.P2PDispatch;
import com.greenfoxacademy.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
  @Autowired
  MessageRepository repository;

  @Autowired
  private SimpMessagingTemplate template;

  @PostMapping("/api/message/receive")
  public OutputMessage receiveMessage(@RequestBody P2PDispatch received) {
    OutputMessage message = new OutputMessage(received.getMessage().getUsername(), received.getMessage().getText());
    if (!received.getClient().getId().equals(System.getenv("CHAT_APP_UNIQUE_ID"))) {
      repository.save(message);
      P2PDispatch.post(received);
    }
    template.convertAndSend("/api/showmessage", message);

    return message;
  }
}
