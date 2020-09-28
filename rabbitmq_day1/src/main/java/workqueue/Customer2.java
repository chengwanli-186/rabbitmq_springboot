package workqueue;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Customer2 {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);  ////让消费者2每次只能消费一个消息
        channel.queueDeclare("work",true,false,false,null);
        //把下行的第二个参数置为false，不让其自动确认消息消费  下行代码中的参数1是队列名称  参数2：消息自动确认  true会自动确认 false不会自动确认
        channel.basicConsume("work",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);   //上面我们关闭了自动消费消息确认，这里我们手动确认
            }
        });
    }
}
