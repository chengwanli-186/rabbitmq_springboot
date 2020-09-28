package utils;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

//该类用来作为工具类，用来将Provider和Customer中的冗余代码抽出，形成一个工具类要使用的时候就拿来用
//方便开发
public class RabbitMQUtils {
    //为了方便我们使用 类名.  的方式来调用，我们提供一个创建链接对象的方法
    public static Connection getConnection(){
        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("192.168.74.131");
            connectionFactory.setPort(5672);
            connectionFactory.setVirtualHost("/ems");
            connectionFactory.setUsername("ems");
            connectionFactory.setPassword("123");
            connectionFactory.newConnection();
            return connectionFactory.newConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //关闭通道和连接的方法
    public static void closeConnectionAndChannel(Channel channel ,Connection conn){
        try{
            if (channel != null)channel.close();
            if (conn != null)conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
