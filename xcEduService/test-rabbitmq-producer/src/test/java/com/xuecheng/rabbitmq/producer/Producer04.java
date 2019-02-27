package com.xuecheng.rabbitmq.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer04 {


    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_TOPICS_INFORM="exchange_topics_inform";

    public static void main(String[] args) {
        Connection connection=null;
        Channel channel=null;

        //创建一个MQ连接
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/"); //RabbitMQ默认虚拟机名称为"/",虚拟机相当于一个独立的mq服务器

        try {
            connection= factory.newConnection();
            channel=connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_TOPICS_INFORM, BuiltinExchangeType.TOPIC);
                                //队列名称             //是否持久化 //是否独占此队列 //队列不用是否自动删除 //参数
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            /**
             *队列绑定交换机  队列名称 交换机名称  routingKey
             */
            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_TOPICS_INFORM,"inform.#.email.#");
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_TOPICS_INFORM,"inform.#.sms.#");

            //发送消息
            for (int i=1;i<=5;i++){
                String message ="email inform to user"+i;
                //向交换机发送消息 String exchange ,String routingKey ,BasicProperties props.byte[] body

                /**
                 * 参数明细
                 * 1.交换机名称,不指定是否默认交换机名称 Default Exchange
                 * 2.routingKey ,根据key名称将消息转发到具体的队列，这里填写队列名称表示消息将发到此队列
                 * 3.消息属性
                 * 4.消息内容
                 */
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,"inform.email",null,message.getBytes());
            }

            //发送消息
            for (int i=1;i<=5;i++){
                String message ="sms inform to user"+i;
                //向交换机发送消息 String exchange ,String routingKey ,BasicProperties props.byte[] body

                /**
                 * 参数明细
                 * 1.交换机名称,不指定是否默认交换机名称 Default Exchange
                 * 2.routingKey ,根据key名称将消息转发到具体的队列，这里填写队列名称表示消息将发到此队列
                 * 3.消息属性
                 * 4.消息内容
                 */
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,"inform.sms",null,message.getBytes());
            }

            //发送消息
            for (int i=1;i<=5;i++){
                String message ="email sms  inform to user"+i;
                //向交换机发送消息 String exchange ,String routingKey ,BasicProperties props.byte[] body

                /**
                 * 参数明细
                 * 1.交换机名称,不指定是否默认交换机名称 Default Exchange
                 * 2.routingKey ,根据key名称将消息转发到具体的队列，这里填写队列名称表示消息将发到此队列
                 * 3.消息属性
                 * 4.消息内容
                 */
                channel.basicPublish(EXCHANGE_TOPICS_INFORM,"inform.email.sms",null,message.getBytes());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
