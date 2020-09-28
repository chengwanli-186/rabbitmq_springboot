package direct;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//最合适的方式是将该部分代码放在静态代码块里面
public class Customer {
//在Junit测试中，不支持多线程模型，如果以@Test形式运行的话，它没有办法去监听我们的MQ，所以这里使用main函数形式来消费MQ中的信息
    //因为Customer不像Provider，因为Provider生产完消息就完事了，而Customer得一直去监听MQ，得让Customer处于一个监听的状态
    //而@Test形式是把该Customer线程直接杀死了
    public static void main(String[] args) throws IOException, TimeoutException {
//        //创建RMQ的连接工厂对象
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        //设置连接RMQ的主机
//        connectionFactory.setHost("192.168.74.129");  //这里注意：本地虚机的ip总会进行漂移，再进行测试时，先查一下本地虚机的ip地址
//        //设置端口号
//        connectionFactory.setPort(5672);
//        //设置连接哪个虚拟主机
//        connectionFactory.setVirtualHost("/ems");
//        //设置访问虚拟主机的用户名和密码
//        connectionFactory.setUsername("ems");
//        connectionFactory.setPassword("123");
//        //获取连接对象
//        Connection connection = connectionFactory.newConnection();

        //通过工具类获取连接对象，该工具类RabbitMQUtils封装了上述被注释的代码
        Connection connection = RabbitMQUtils.getConnection();
        //通过连接获取连接中的通道对象
        Channel channel = connection.createChannel();
        //绑定通道对应的消息队列
        //参数1：队列名称 如果队列不存在，则自动创建，将通道和队列进行绑定
        //参数2：用来定义队列特性是否要持久化 true 持久化队列 false 不持久化队列
        //参数3：exclusive 是否独占队列 true独占队列 false不独占队列
        //参数4：autoDelete：是否在消费完成后自动删除队列 true 自动删除  false 不自动删除
        //参数5：额外附件参数
        channel.queueDeclare("hello",false,false,false,null);

        //消费消息
        channel.basicConsume("hello",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("=============================" + new String(body));
            }
        });
        //如果用了下面两行代码，则Customer消费完一个消息就关闭了
        //如果想让Customer持续与MQ保持联系，则放开这两行代码即可
//        channel.close();
//        connection.close();
        //调用工具类去关闭channel、connection
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
