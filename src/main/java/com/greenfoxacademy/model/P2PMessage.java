package com.greenfoxacademy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.swing.text.DateFormatter;
import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class P2PMessage {
  final static long ID_LOWEST = 1000000;
  final static long ID_HIGHEST = 9999999;

  @Id
  long id;
  String username;
  String text;
  Timestamp timestamp;

  public P2PMessage(String username, String text) {
    id = (long)(Math.random() * (ID_HIGHEST - ID_LOWEST)) + ID_LOWEST;
    this.username = username;
    this.text = text;
    timestamp = new Timestamp(System.currentTimeMillis());
  }
}
