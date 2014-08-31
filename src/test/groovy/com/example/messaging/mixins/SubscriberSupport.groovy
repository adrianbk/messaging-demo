package com.example.messaging.mixins

import com.example.messaging.SpamListener

class SubscriberSupport {
  def spamListener() {
    return new SpamListener()
  }
}
