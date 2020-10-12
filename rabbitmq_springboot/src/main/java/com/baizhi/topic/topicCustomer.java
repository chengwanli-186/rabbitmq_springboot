package com.baizhi.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class topicCustomer {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,  //创建一个临时队列
                    exchange = @Exchange(type = "topic" , name = "topics2"),
                    key = {"user.save","user.*"}
            )
    })
    public void receive1(String message){
        System.out.println("message1 = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,  //创建一个临时队列
                    exchange = @Exchange(type = "topic" , name = "topics2"),
                    key = {"order.#","product.#","user.*"}
            )
    })
    public void receive2(String message){
        System.out.println("message2 = " + message);
    }
}
