package com.example.messaging;

public interface Subscriber {
  void accept(Message message);
}
