package com.example.messaging;

import java.util.UUID;

public class Gateway {
  public String send(Message message) {
    return UUID.randomUUID().toString();
  }

}
