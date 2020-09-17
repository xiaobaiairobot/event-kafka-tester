package com.yunli.bigdata.eventbus.example.service.impl;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.yunli.bigdata.eventbus.example.config.KafkaConfiguration;

/**
 * @author david
 * @date 2020/7/28 7:54 下午
 */
@Component
public class KafkaReceiver {

  private final Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);

  private final KafkaConfiguration kafkaConfiguration;

  @Autowired
  public KafkaReceiver(KafkaConfiguration kafkaConfiguration) {
    this.kafkaConfiguration = kafkaConfiguration;
  }

  @KafkaListener(topics = "${targetTopicName}")
  public void listenTopic(ConsumerRecord<?, ?> record) {
    Optional<?> kafkaMessage = Optional.ofNullable(record.value());
    if (kafkaMessage.isPresent()) {
      Object message = kafkaMessage.get();
      logger.info("received message :" + message);
    }
  }
}
