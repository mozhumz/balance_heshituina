//package com.mozhumz.balance.config.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.messaging.support.MessageBuilder;
//
//
//@EnableBinding(Source.class)
//@Slf4j
//public class KafkaProducer {
//
//
//    @Autowired
//    private Source source;
//
//    public void sendMessage(String message) {
//        try {
//            source.output1().send(MessageBuilder.withPayload("message: " + message).build());
//        } catch (Exception e) {
//            log.info("消息发送失败，原因："+e);
//            e.printStackTrace();
//        }
//    }
//}
