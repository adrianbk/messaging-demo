package com.example.messaging.api;

import com.example.messaging.AuditService;
import com.example.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audit")
public class AuditController {

  @Autowired
  private AuditService auditService;

  @RequestMapping(value="/spam/{messageId}", method= RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public Message getMessage(@PathVariable String messageId) {
    Message message = auditService.get(messageId);
    return message;
  }

  @RequestMapping(value = "/spam", method=RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  public void addMessage(@RequestBody Message message) {
    auditService.spam(message);
  }
}
