package com.example.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class SpamListener implements Subscriber {
  private static final Logger logger = LoggerFactory.getLogger(SpamListener.class);
  private AuditService auditService = new AuditService();
  private List<String> blackList = Arrays.asList(new String[]{"escort", "terror", "viagra"});

  public void accept(Message message) {
    logger.info("Accepted message {}", message);

    String content = message.getContent();
    if (blackList.contains(content)) {
      logger.error("Nasty content {}", message);
      auditService.spam(message);
    }

  }
}
