package routingdirect;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "logs_direct";
        //通过通道声明要绑定的交换机  参数1：交换机名称  参数2：路由模式  这里是direct模式
        channel.exchangeDeclare(exchangeName,"direct");
        //发送消息前---
        //---路由模式要在发送消息前指定要发送的消息需要通过哪个路由来发送到我们准备要发送消息到的队列中
        String routingKey = "warning";  //指定路由，这里指定的路由为warning，生产者将消息发送到对应的交换机里面，然后交换机通过指定的路由去将消息发送到对应的路由
        //后面如果队列和该路由warning绑定，则该路由的信信息就能由相应的队列进行消费
        //如果生产者发送的消息到了一个队列没有绑定的路由，则该路由的消息不会被任何消费者消费

        //发送消息，第四个参数为要发送的消息内容
        channel.basicPublish(exchangeName,routingKey,null,("这是direct模型发布的基于route key：[" + routingKey +"] 发送的消息").getBytes());

        //关闭资源
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
