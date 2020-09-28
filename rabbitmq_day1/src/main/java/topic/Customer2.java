package topic;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

public class Customer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机以及交换机类型
        channel.exchangeDeclare("topics","topic");
        //创建一个临时队列
        String queue = channel.queueDeclare().getQueue();
        //绑定队列和交换机并声明路由   这里使用动态路由，即通配符形式route key
        channel.queueBind(queue,"topics","user.#");
        //这里  user.#  后面的#号是来匹配user后面的0个或所有单词的，如果是  user.*  则只会匹配user后面的一个单词

        //  #   会匹配0个或所有的单词
        //  *   只会匹配一个单词

        //消费消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：" + new String(body));
            }
        });
    }
}
