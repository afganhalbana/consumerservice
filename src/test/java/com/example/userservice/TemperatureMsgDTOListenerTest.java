package com.example.userservice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@RunWith(SpringRunner.class)
class TemperatureMsgDTOListenerTest {

  @Autowired
  private TemperatureListener temperatureListener;

  @Autowired
  private Sink sink;

  @Test
  public void testShouldConsumeTemperatureMessage() throws Throwable {
    String name = "test";
    Message<TemperatureMsgDTO> msg = MessageBuilder.withPayload(new TemperatureMsgDTO(name)).build();
    SubscribableChannel input = sink.input();
    input.send(msg);
    then(this.temperatureListener.name).isEqualTo(name);
  }

}