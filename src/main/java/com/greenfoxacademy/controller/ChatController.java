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
  public P2PDispatch send(Message message) throws Exception
  {
    OutputMessage msg = new OutputMessage(message.getFrom(), message.getText());
    repository.save(msg);
    P2PDispatch dispatch = new P2PDispatch(
        new P2PMessage(message.getFrom(), message.getText()),
        new P2PClient(System.getenv("CHAT_APP_UNIQUE_ID")));
    P2PDispatch.post(dispatch);

    return dispatch;
  }

  @PostMapping("/api/message/receive")
  public Status receiveMessage(@RequestBody P2PDispatch received) {
    OutputMessage message = new OutputMessage(received.getMessage().getUsername(), received.getMessage().getText());
    Status status = new Status("ok");
    if (!received.getClient().getId().equals(System.getenv("CHAT_APP_UNIQUE_ID"))) {
      repository.save(message);
      status = P2PDispatch.post(received);
    }
    return status;
  }

}
