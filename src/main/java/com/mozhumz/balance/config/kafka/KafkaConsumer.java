//package com.mozhumz.balance.config.kafka;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.stream.annotation.EnableBinding;
//import org.springframework.cloud.stream.annotation.StreamListener;
//
//import javax.annotation.Resource;
//
//@EnableBinding(Sink.class)
//@Slf4j
//public class KafkaConsumer {
//
//
//    @StreamListener(Sink.INPUT_1)
//    private void receive(String vote) {
//        System.out.println("receive message:"+vote);
//    }
//
//}
