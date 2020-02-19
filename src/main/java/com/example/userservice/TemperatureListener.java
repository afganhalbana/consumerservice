package com.example.userservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TemperatureListener {
  String name;

  @StreamListener(Sink.INPUT)
  public void onUserMessage(TemperatureMsgDTO u) {
    this.name = u.getName();
    log.info(this.name);
  }
}
