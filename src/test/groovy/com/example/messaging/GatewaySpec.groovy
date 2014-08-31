package com.example.messaging

import spock.lang.Specification

class GatewaySpec extends Specification {

  def "should get a UUID when a message is submitted"() {
    given: "a basic message"
      Message message = new Message()
      Gateway gateway = new Gateway()

    when: "a message is sent"
      String uuid = gateway.send(message)

    then: "a UUID is returned"
      uuid.length() == 36
  }

}