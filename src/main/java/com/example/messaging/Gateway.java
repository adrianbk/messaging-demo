package com.example.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.util.CollectionUtils.isEmpty;

public class Gateway {

  private List<Subscriber> subscribers = new ArrayList();

  public String send(Message message) {
    String id = UUID.randomUUID().toString();
    message.setId(id);

    if (isEmpty(subscribers)) {
      throw new IllegalArgumentException("No subscribers");
    }

    for (Subscriber subscriber : subscribers) {
      subscriber.accept(message);
    }
    return id;
  }

}
