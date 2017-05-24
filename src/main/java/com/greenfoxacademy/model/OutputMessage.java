package com.greenfoxacademy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "messages")
public class OutputMessage
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String username;
  private String message;
  private String time;

  public OutputMessage(String from,String message)
  {
    this.username = from;
    this.message = message;
    this.time = new SimpleDateFormat("HH:mm:ss").format(new Date());
  }
}
