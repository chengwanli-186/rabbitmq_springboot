package com.baizhi.Fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutCustomer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,  //创建一个临时队列
                    exchange = @Exchange(value = "logs2",type = "fanout") //绑定的交换机
                    //这里说明一下：我们在fanout模式中，生产者那里建立了一个交换机，然后让消费者绑定生产者建立的交换机
                    //另外：生产者那里建立了交换机，然后消费者这里建立队列并绑定交换机，之后就可以进行信息内容的消费了
            )
    })
    public void receive2(String message){
        System.out.println("message1 = " + message);

    }


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,  //创建一个临时队列
                    exchange = @Exchange(value = "logs2",type = "fanout") //绑定的交换机
                    //这里说明一下：我们在fanout模式中，生产者那里建立了一个交换机，然后让消费者绑定生产者建立的交换机
                    //另外：生产者那里建立了交换机，然后消费者这里建立队列并绑定交换机，之后就可以进行信息内容的消费了
            )
    })
    public void receive1(String message){
        System.out.println("message2 = " + message);

    }


}
