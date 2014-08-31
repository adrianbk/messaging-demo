package com.example.messaging.mixins

import com.example.messaging.Message

class MessageSupport {
  def validMessage(){
    new Message(content: "Have a nice day!")
  }
}
