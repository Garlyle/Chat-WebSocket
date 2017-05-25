package com.greenfoxacademy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Getter
public class P2PDispatch {
  public static Status post(P2PDispatch dispatch) {
    RestTemplate template = new RestTemplate();
    String url = System.getenv("CHAT_APP_PEER_ADDRESS") + "api/message/receive";
    Status status = new Status();
    try {
      status = template.postForObject(url, dispatch, Status.class);
    } catch (Exception e) {
      System.out.println(e.getStackTrace());
    }
    return status;
  }

  P2PMessage message;
  P2PClient client;
}
