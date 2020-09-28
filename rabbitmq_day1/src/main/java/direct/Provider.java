package direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
//        //创建RMQ的连接工厂对象
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        //设置连接RMQ的主机
//        connectionFactory.setHost("192.168.74.129");
//        //设置端口号
//        connectionFactory.setPort(5672);
//        //设置连接哪个虚拟主机
//        connectionFactory.setVirtualHost("/ems");
//        //设置访问虚拟主机的用户名和密码
//        connectionFactory.setUsername("ems");
//        connectionFactory.setPassword("123");
//        获取连接对象
//        Connection connection = connectionFactory.newConnection();

        //通过工具类获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        //通过连接获取连接中的通道对象
        Channel channel = connection.createChannel();
        //绑定通道对应的消息队列
        //参数1：队列名称 如果队列不存在，则自动创建该名称的队列
        //参数2：用来定义队列是否要持久化 true 持久化队列，存到磁盘上，false 不持久化队列，不存到磁盘上，设置为false，则重启RabbitMQ后队列就会被删掉
        //参数3：exclusive 是否独占队列 true独占队列 false不独占队列，独占队列即该对列只可以由该通道绑定
        //参数4：autoDelete：是否在消费完成后自动删除队列 true 自动删除  false 不自动删除，当队列中没有其他消息，是否要把该对列删除
        //注意，只要当队列中没有其他消息且消费者与队列断开连接后，该对列才会被删除
        //参数5：额外附件参数
        channel.queueDeclare("world",false,false,false,null);
        //发布消
        //参数1：交换机名称 参数2：队列名称
        //参数3：传递消息额外设置，可以设置是否将消息持久化，正常情况下我们的消息是在内存中的，重启RabbitMQ后就会别删除，
        //如果设置了持久化，则会存到磁盘上面，再次启动Rabbitmq，则消息会重新加载出来  参数4：消息的具体内容
        //注意：生产者和消费者进行沟通的队列在各方面要保持一致，例如持久化就都持久化，比如生产者的aa队列持久化了，消费者就不能找没有持久化也叫aa的队列去消费消息
        channel.basicPublish("","world",null,"hello rabbitmq".getBytes());
        channel.close();
        connection.close();
        //注意：一个通道可以绑定多个队列，这里绑定hello队列：channel.queueDeclare("hello",false,false,false,null);
        //但是要往哪个队列中发消息，是要进行指定的，例如：channel.basicPublish("","hello",null,"hello rabbitmq".getBytes());往hello队列中发消息
    }
}
