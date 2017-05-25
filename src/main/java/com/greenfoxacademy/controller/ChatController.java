package com.greenfoxacademy.controller;

import com.greenfoxacademy.model.*;
import com.greenfoxacademy.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
  @SendTo("/api/showmessage")
  public OutputMessage send(Message message) throws Exception
  {
    OutputMessage msg = new OutputMessage(message.getFrom(), message.getText());
    repository.save(msg);
    P2PDispatch dispatch = new P2PDispatch(
        new P2PMessage(message.getFrom(), message.getText()),
        new P2PClient(System.getenv("CHAT_APP_UNIQUE_ID")));
    P2PDispatch.post(dispatch);

    return msg;
  }
}
