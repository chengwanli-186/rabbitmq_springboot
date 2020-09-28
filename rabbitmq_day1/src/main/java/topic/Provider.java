package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明交换机以及交换机的类型
        channel.exchangeDeclare("topics","topic");
        //声明路由(这里采用topic路由模式的通配符形式路由形式)
        String routekey = "user.save.findAll";
        //发布消息
        channel.basicPublish("topics",routekey,null,("这里是topic动态路由模型,routekey:[" + routekey + "]").getBytes());
        //关闭资源
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
