//package com.mozhumz.balance.config.activemq;
//
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ActivemqConsumer2 {
//
//    @JmsListener(destination = "publish.queue", containerFactory = "jmsListenerContainerQueue")
//    @SendTo("out.queue")
//    public String receive2(String text){
//        System.out.println("QueueListener2: consumer-a 收到一条信息: " + text);
//        return "consumer-a received : " + text;
//    }
//
//    @JmsListener(destination = "publish.topic", containerFactory = "jmsListenerContainerTopic")
//    public void receiveTopic2(String text){
//        System.out.println("TopicListener2: consumer-a 收到一条信息: " + text);
//    }
//}