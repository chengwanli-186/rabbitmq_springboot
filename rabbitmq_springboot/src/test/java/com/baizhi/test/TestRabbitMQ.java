package com.baizhi.test;

import com.baizhi.RabbitmqSpringbootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = RabbitmqSpringbootApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    //注入RabbitTmplate对象，用来操作RabbitMQ
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //direct模式：hello world 生产者只需要进行消息的发送，消费者去消费消息就可以了
    @Test
    public void testdirect(){  //这里就模拟一个生产者来生产消息，一个消费者消费消息，即direct模式
        //这里，第一个参数是队列的名称；第二个参数是要发送的消息内容
        rabbitTemplate.convertAndSend("hello2" , "hello world direct module");
    }


    //work模式
    @Test
    //说明：默认在Spring AMQP实现中Work模式是公平调度，如果需要实现能者多劳则需要额外配置
    public void testwork(){   //这里就模拟一个生产者来生产消息，多个消费者消费消息，即workqueue模式
        //RabbitMQ中work模式的消息消费是公平消费，即生产者生产的消息是公平的分配给每一个消费者进行消费的，所以我们当前项目的消费者1和2在消费消息时
        //都是轮询消费，一个接一个的消费
        for (int i = 0 ; i < 10 ; i++){
            rabbitTemplate.convertAndSend("work2","work module " + i);
        }
    }


    //fanout模式：即广播模式
    @Test
    public void testFanout(){
        rabbitTemplate.convertAndSend("logs2" , "", "Fanout模式发送的消息");
    }


    @Test
    //routing模式：即路由模式
    public void testRoute(){
        rabbitTemplate.convertAndSend("routingexchange2" , "error" , "发送info的key的路由信息");
    }


    //topic 动态路由：即订阅模式
    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend("topics2","product.save.add","product.save 路由的消息");
    }



}
