package com.xuecheng.rabbitmq.producer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Producer05 {

    //队列名称
    private static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_HEADER_INFORM="exchange_headers_inform";

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

        try{
            connection=factory.newConnection();
            channel=connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_HEADER_INFORM,BuiltinExchangeType.HEADERS);

            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
          //  channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            Map<String,Object> headers_email= new HashMap<String,Object>();
            headers_email.put("inform_type","email");//匹配email通知消息者绑定header

            Map<String,Object> headers_sms= new HashMap<String,Object>();
            headers_email.put("inform_type","sms");//匹配sms通知消息者绑定header

            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_HEADER_INFORM,"",headers_email);
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_HEADER_INFORM,"",headers_sms);

            String message="email inform to user";
            Map<String,Object> headers= new HashMap<String,Object>();
            headers.put("inform_type","email");//匹配email通知消息者绑定header
            headers.put("inform_type","sms");//匹配sms通知消费者绑定的header
            AMQP.BasicProperties.Builder properties=new AMQP.BasicProperties.Builder();
            properties.headers(headers);
            //Email通知
            channel.basicPublish(EXCHANGE_HEADER_INFORM,"",properties.build(),message.getBytes());

        }catch(Exception e){
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
