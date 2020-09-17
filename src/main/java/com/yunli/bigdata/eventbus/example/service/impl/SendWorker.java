package com.yunli.bigdata.eventbus.example.service.impl;

import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.yunli.bigdata.dsep.foundation.util.DateUtil;
import com.yunli.bigdata.eventbus.example.config.KafkaConfiguration;

/**
 * @author david
 * @date 2020/7/29 10:44 上午
 */
public class SendWorker implements Runnable {

  private final Logger logger = LoggerFactory.getLogger(SendWorker.class);


  private final KafkaConfiguration kafkaConfiguration;

  private final KafkaProducer kafkaProducer;

  private AtomicInteger index = new AtomicInteger(0);

  public SendWorker(KafkaTemplate<String, String> kafkaTemplate,
      KafkaConfiguration kafkaConfiguration) {
    this.kafkaConfiguration = kafkaConfiguration;
    this.kafkaProducer = buildKafkaSink();
  }

  @Override
  public void run() {
    for (int i = 0; i < kafkaConfiguration.getSendTimes(); i++) {
      String msg = getRandomMessage();
      kafkaProducer
          .send(new ProducerRecord<>(kafkaConfiguration.getSourceTopic(), "test", getRandomMessage()), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
              if (e != null) {
                logger.debug(e.getMessage());
              } else {
                logger.debug(String.format("receive the result: %s", recordMetadata));
              }
            }
          });
      if (i % 1000 == 0) {
        kafkaProducer.flush();
      }
    }
    kafkaProducer.flush();
    logger.info("消息发送已完成" + kafkaConfiguration.getSendTimes());
  }


  private KafkaProducer buildKafkaSink() {
    Properties props = new Properties();
    props.put("bootstrap.servers", this.kafkaConfiguration.getServer());
    props.put("allow.auto.create.topics", "false");
    props.put("request.required.acks", "0");
    props.put("producer.type", "async");
    props.put("key.serializer", StringSerializer.class.getName());
    props.put("value.serializer", StringSerializer.class.getName());
    props.setProperty("client.id", UUID.randomUUID().toString());
    return new KafkaProducer<>(props);
  }

  private String getRandomMessage() {
    // id name sex age birthday
    Double dInfo = Math.floor(Math.random() * (100 - 1)) + 1;
//    String strMessage = String.format("%d,\"%s\",\"%s\",%d,\"%s\"", index.incrementAndGet(), "张三" + index.get(), "男",
//        dInfo.intValue(), DateUtil.toSimpleString(new Date()));
    // String strMessage = String.format("\"%s\"", "张三" + index.incrementAndGet());
    String strMessage = String.format("\"%s\"", "acfun");
    // System.out.println(strMessage);
    return strMessage;
  }
}
