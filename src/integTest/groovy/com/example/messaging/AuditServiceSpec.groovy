package com.example.messaging

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
@IntegrationTest
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
class AuditServiceSpec extends Specification {

  @Autowired
  AuditService auditService

  def "should save a message"() {
    Message message = new Message(id: 'any', content: 'someContent')
    when:
      auditService.spam(message)
    then:
      def result = auditService.get(message.id)
      result.content == 'someContent'
      result.id == 'any'
  }

  def "Spring transaction stuff should rollback previous test data"() {
    expect:
      null == auditService.get('any')
  }
}
