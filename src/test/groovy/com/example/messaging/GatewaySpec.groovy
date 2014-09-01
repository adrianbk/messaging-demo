package com.example.messaging

import com.example.messaging.mixins.MessageSupport
import com.example.messaging.mixins.SubscriberSupport
import spock.lang.Specification

@Mixin([MessageSupport, SubscriberSupport])
class GatewaySpec extends Specification {

  def "should get a UUID when a message is submitted"() {
    given: "a basic message"
      Message message = new Message(content: "Hello")
      Gateway gateway = new Gateway(subscribers: [spamListener()])

    when: "a message is sent"
      String uuid = gateway.send(message)

    then: "a UUID is returned"
      uuid.length() == 36
  }

  def "Should publish a message to all subscribers"(){
    given:
      SpamListener spamListener = Mock()
      def subscribeList = [spamListener]
      Gateway gateway = new Gateway(subscribers: subscribeList)
      def message = validMessage()
    when:
      gateway.send(message)
    then:
      1 * spamListener.accept(message)
  }

  def "should throw when no subscribers"(){
    when:
      new Gateway().send(validMessage())

    then:
      def ex = thrown IllegalArgumentException
      ex.message == "No subscribers"
  }

  def "should mock"(){
    given:
      def auditService = Mock(AuditService)
      auditService.get(_) >> new Message(content: 'hello')

    when:
      def message = auditService.get("df")
    then:
      message.content == 'hello'

  }


}