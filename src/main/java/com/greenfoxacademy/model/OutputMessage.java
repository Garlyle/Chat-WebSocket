package com.greenfoxacademy.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OutputMessage
{
  private String from;
  private String message;
  private String time;

  public OutputMessage() {}

  public OutputMessage(String from,String message)
  {
    this.from = from;
    this.message = message;
    this.time = new SimpleDateFormat("HH:mm:ss").format(new Date());
  }

  public String getFrom()
  {
    return from;
  }

  public void setFrom(String from)
  {
    this.from = from;
  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public String getTime()
  {
    return time;
  }
}
