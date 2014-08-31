package com.example.messaging;

import com.example.messaging.persistence.AuditMessage;
import com.example.messaging.persistence.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

  @Autowired
  private AuditRepository repository;

  public Message get(String id) {
    AuditMessage auditMessage = repository.findOne(id);
    if (null != auditMessage) {
      Message message = map(auditMessage);
      return message;
    }
    return null;
  }

  public void spam(Message message) {
    AuditMessage auditMessage = map(message);
    repository.save(auditMessage);
  }

  private AuditMessage map(Message message) {
    AuditMessage auditMessage = new AuditMessage();
    auditMessage.setId(message.getId());
    auditMessage.setContent(message.getContent());
    return auditMessage;
  }

  private Message map(AuditMessage auditMessage) {
    Message message = new Message();
    message.setContent(auditMessage.getContent());
    message.setId(auditMessage.getId());
    return message;
  }
}
