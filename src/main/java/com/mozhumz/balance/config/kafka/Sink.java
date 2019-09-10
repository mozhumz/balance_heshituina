//package com.mozhumz.balance.config.kafka;
//
//import org.springframework.cloud.stream.annotation.Input;
//import org.springframework.messaging.SubscribableChannel;
//
///**
// * 输入通道
// */
//public interface Sink {
//
//    //接收队列1 跟配置文件里面的通道名称 testa 保持一致
//    String INPUT_1 = "testa";
//
//    @Input(Sink.INPUT_1)
//    SubscribableChannel input1();
//
//}
