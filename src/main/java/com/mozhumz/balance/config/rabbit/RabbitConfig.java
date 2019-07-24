package com.mozhumz.balance.config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description rabbitmq配置类
 */
@Configuration
public class RabbitConfig {

    public static final String fanoutExchange = "fanout-exchange-saveUser";
    public static final String addUserQueue = "balance-saveUserQueue";


    //声明队列
    @Bean
    public Queue queueTest() {

        /**
         * durable： 是否持久化, 队列的声明默认是存放到内存中的，如果rabbitmq重启会丢失，
         * 如果想重启之后还存在就要使队列持久化，保存到Erlang自带的Mnesia数据库中，当rabbitmq重启之后会读取该数据库
         * exclusive：是否排外的，有两个作用，一：当连接关闭时connection.close()该队列是否会自动删除；
         * 二：该队列是否是私有的private，如果不是排外的，可以使用两个消费者都访问同一个队列，没有任何问题，
         * 如果是排外的，会对当前队列加锁，其他通道channel是不能访问的，如果强制访问会报异常
         * autoDelete：是否自动删除，当最后一个消费者断开连接之后队列是否自动被删除，
         * 可以通过RabbitMQ Management，查看某个队列的消费者数量，当consumers = 0时队列就会自动删除
         */
//        return new Queue("test", false, true, true);
        return new Queue("test");
    }

    @Bean
    public Queue addUserQueue() {
        return new Queue(addUserQueue);
    }

//    @Bean
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(){
//        SimpleRabbitListenerContainerFactory factory=new SimpleRabbitListenerContainerFactory();
//        factory.setConcurrentConsumers(50);
////        factory.setConnectionFactory(connectionFactory());
//        factory.setPrefetchCount(1);
//        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
//
//        return factory;
//    }

//    @Bean
//    public ConnectionFactory connectionFactory(){
//        CachingConnectionFactory factory=new CachingConnectionFactory(host,port);
//        factory.setUsername(username);
//        factory.setPassword(password);
//
//        return factory;
//    }
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }

    //将队列与fanout交换机进行绑定
    @Bean
    Binding fanoutBinding() {
        return BindingBuilder.bind(addUserQueue()).to(fanoutExchange());
    }

//    @Bean
//    Binding fanoutBindingTest() {
//        return BindingBuilder.bind(queueTest()).to(fanoutExchange());
//    }


}
