package workqueue;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Customer1 {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.basicQos(1);  //让消费者1每次只能消费一个消息，不会造成消费者一次拿了许多消息但由于消费速度慢，如果在消费的过程中，该消费者宕机，会造成消息的丢失
        channel.queueDeclare("work",true,false,false,null);
        //把下行的第二个参数置为false，不让其自动确认消息消费  下行代码中的参数1是队列名称  参数2：消息自动确认  true会自动确认 false不会自动确认
        channel.basicConsume("work",false,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try{
                    Thread.sleep(1000);   //把消费者-1处理消息的速度变慢
                    //当消费者消费信息的速度慢的时候，就会拖垮我们的系统
                }catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println("消费者-1：" + new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);   //上面我们关闭了自动消费消息确认，这里我们手动确认，作用就是消费者确认消费一个消息，生产者就派发给该消费者一个
                //确认一个发一个，确认一个发一个，这样就避免了信息的丢失
                //自动确认机制会造成消息的丢失，所以我们一般会进行手动确认
            }
        });
    }
}
