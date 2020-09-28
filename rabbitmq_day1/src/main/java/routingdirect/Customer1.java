package routingdirect;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Customer1 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "logs_direct";

        //通道声明连接哪个交换机以及该交换机的类型、
        channel.exchangeDeclare(exchangeName,"direct");
        //创建一个临时队列，把消费者和交换机联系起来
        String queue = channel.queueDeclare().getQueue();
        //基于某route Key将队列和交换机建立起联系，即绑定。例如：下行代码，第二个参数为交换机的名称，第三个参数为基于的route key
        //该行代码就会让消费者会去logs_direct交换机中的route key为error的消息队列中去消费消息
        channel.queueBind(queue,exchangeName,"error");  //这里将error路由和交换机logs_direct进行绑定
        //获取消费的消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-1中的消息" + new String(body));
            }
        });
    }
}
