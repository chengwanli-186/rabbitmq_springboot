package com.baizhi.hellotwodirect;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component  //@Component保证该组件能够被扫描到
//下面一行代码中，@Queue("hello2")中参数也有好多个，比如该对列是否持久化，是否自动删除等...队列相关的设置可以在该参数中进行设置
//实际上，@Queue("hello2")中创建的队列默认属性是：持久化、非独占、不是自动删除队列，即true、false、false
@RabbitListener(queuesToDeclare = @Queue("hello2"))//@RabbitListener表示RabbitMQ的消费者，来监听生产者生产的消息，这里监听的是hello2队列里的消息
public class HelloCustomer {

    @RabbitHandler  //@RabbitHandler表示在从生产者中取出相应的消息后，用该注解注释的方法来处理相应的消息
    public void receive1(String message){  //这里是消费者进行消息内容的消费
        System.out.println("message = " + message);
    }










}
