package com.greenfoxacademy.controller;

import com.greenfoxacademy.model.Message;
import com.greenfoxacademy.model.OutputMessage;
import com.greenfoxacademy.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController
{
  @Autowired
  MessageRepository repository;

  @MessageMapping("/chat/")
  @SendTo("/messages")
  public OutputMessage send(Message message) throws Exception
  {
    OutputMessage msg = new OutputMessage(message.getFrom(), message.getText());
    repository.save(msg);

    return msg;
  }
}

