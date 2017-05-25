package com.greenfoxacademy.controller;

import com.greenfoxacademy.model.*;
import com.greenfoxacademy.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class ChatController
{
  @Autowired
  MessageRepository repository;

  @RequestMapping("/")
  public String index(Model model) {
    model.addAttribute("messages", repository.findAll());

    return "index";
  }

  @MessageMapping("/postMessage")
  @SendTo("/api/message/receive")
  public OutputMessage send(Message message) throws Exception
  {
    OutputMessage msg = new OutputMessage(message.getFrom(), message.getText());
    repository.save(msg);
    P2PDispatch.post(new P2PDispatch(
        new P2PMessage(message.getFrom(), message.getText()),
        new P2PClient(System.getenv("CHAT_APP_UNIQUE_ID"))));

    return msg;
  }

  @PostMapping("/api/message/receive")
  @SendTo("/api/message/receive")
  public OutputMessage receiveMessage(@RequestBody P2PDispatch received) {
    OutputMessage message = new OutputMessage(received.getMessage().getUsername(), received.getMessage().getText());
    if (!received.getClient().getId().equals(System.getenv("CHAT_APP_UNIQUE_ID"))) {
      repository.save(message);
      P2PDispatch.post(received);
    }
    return message;
  }

}
