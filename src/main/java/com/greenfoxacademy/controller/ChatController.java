package com.greenfoxacademy.controller;

import com.greenfoxacademy.model.Message;
import com.greenfoxacademy.model.OutputMessage;
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
  @SendTo("/api/message/receive")
  public OutputMessage send(Message message) throws Exception
  {
    OutputMessage msg = new OutputMessage(message.getFrom(), message.getText());
    repository.save(msg);

    return msg;
  }
}

