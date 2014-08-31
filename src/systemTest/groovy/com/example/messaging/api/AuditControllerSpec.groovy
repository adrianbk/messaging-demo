package com.example.messaging.api

import groovy.json.JsonBuilder
import groovyx.net.http.RESTClient
import spock.lang.Specification

class AuditControllerSpec extends Specification {

  def "should add and retrieve a spam message"() {
    given:
      String messageId = 'sysTestId'
      def json = new JsonBuilder()
      json {
        id messageId
        content 'someContent'
      }
      def http = new RESTClient('http://localhost:8080')
    when:
      def response = http.post(
              contentType: 'application/json',
              path: '/audit/spam',
              body: json.toString()
      )

    then:
      assert response.status == 201

    and:
      def messageResponse = http.get(path: "/audit/spam/${messageId}")
      messageResponse.status == 200
      messageResponse.contentType == 'application/json'
      messageResponse.data.id == 'sysTestId'
      messageResponse.data.content == 'someContent'
  }

  def "Gstrings make json easy"() {
    given:
      def json = new JsonBuilder()
      json {
        id 'someid'
        content 'someContent'
      }
    expect:
      json.toPrettyString() ==
              '''{
    "id": "someid",
    "content": "someContent"
}'''
  }
}
