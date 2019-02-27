package com.xuehcneg.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;

public class Consumer_Header01 {

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
            channel.exchangeDeclare(EXCHANGE_HEADER_INFORM, BuiltinExchangeType.HEADERS);
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);

            HashMap<String, Object> header_email = new HashMap<>();
            header_email.put("inform_email","email");


            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_HEADER_INFORM,"",header_email);

            DefaultConsumer consumer=new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    final String message = new String(body, "UTF-8");
                    System.out.println(message);
                }
            };
            //指定消费队列
            channel.basicConsume(QUEUE_INFORM_EMAIL,true,consumer);
        }catch(Exception e){

        }
    }
}
