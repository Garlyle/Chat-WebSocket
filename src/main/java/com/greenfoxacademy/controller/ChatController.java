package com.greenfoxacademy.controller;

import com.greenfoxacademy.model.Message;
import com.greenfoxacademy.model.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController
{
  @MessageMapping("/chat/")
  @SendTo("/messages")
  public OutputMessage send(Message message) throws Exception
  {
    return new OutputMessage(message.getFrom(), message.getText());
  }
}

