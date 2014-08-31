package com.example.messaging

import com.example.messaging.mixins.LoggingSupport
import com.example.messaging.mixins.MessageSupport
import org.slf4j.Logger
import spock.lang.Specification
import spock.lang.Unroll

@Mixin([LoggingSupport, MessageSupport])
class SpamListenerSpec extends Specification {
  def message
  def logger
  SpamListener listener

  def setup() {
    message = validMessage()
    logger = Mock(Logger)
    listener = new SpamListener()
    mockLogging(listener, logger)
  }

  def "test accept"() {
    when:
      listener.accept(message)
    then:
      1 * logger.info(_, message)
  }

  @Unroll
  def "Should log when dodgy content"() {
    def message = new Message(content: text)
    when:
      listener.accept(message)
    then:
      times * logger.error(_, message)
    where:
      text       | times
      'escort'   | 1
      'terror'   | 1
      'viagra'   | 1
      'friendly' | 0
      null       | 0
      ''         | 0
  }

  def "Should add send spam to the audit service"() {
    given:
      def message = new Message(content: 'terror')
      AuditService auditService = Mock()
      listener.auditService = auditService
    when:
      listener.accept(message)
    then:
      1 * auditService.spam(message) >> { Message cMessage ->
        assert cMessage.content == 'terror'
      }
  }
}
