//package com.mozhumz.balance.config.activemq;
//
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ActivemqConsumer {
//
//    @JmsListener(destination = "publish.queue", containerFactory = "jmsListenerContainerQueue")
//    @SendTo("out.queue")
//    public String receive(String text){
//        System.out.println("QueueListener: consumer-a 收到一条信息: " + text);
//        return "consumer-a received : " + text;
//    }
//
//    @JmsListener(destination = "publish.topic", containerFactory = "jmsListenerContainerTopic")
//    public void receiveTopic(String text){
//        System.out.println("TopicListener: consumer-a 收到一条信息: " + text);
//    }
//}