package com.baizhi.workmodule;


import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkCustomer {

    //第一个消费者
    @RabbitListener(queuesToDeclare = @Queue("work2"))  //@RabbitListener注解也可加在方法上面，代表该方法会处理当前该@RabbitListener所监听的生产者所发布的消息
    public void receive1(String message){
        System.out.println("message1 = " + message);
    }

    //第二个消费者
    @RabbitListener(queuesToDeclare = @Queue("work2"))  //@RabbitListener注解也可加在方法上面，代表该方法会处理当前该@RabbitListener所监听的生产者所发布的消息
    public void receive2(String message){
        System.out.println("message2 = " + message);
    }


}
