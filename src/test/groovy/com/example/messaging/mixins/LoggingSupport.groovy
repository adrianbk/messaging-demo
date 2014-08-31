package com.example.messaging.mixins

import java.lang.reflect.Field
import java.lang.reflect.Modifier

class LoggingSupport {

  def mockLogging(instance, mock, aField = 'logger'){
    Field field = instance.class.getDeclaredField(aField);
    field.setAccessible(true)

    Field modifiers = Field.class.getDeclaredField("modifiers");
    modifiers.setAccessible(true)
    modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

    field.set(instance, mock)
  }
}
