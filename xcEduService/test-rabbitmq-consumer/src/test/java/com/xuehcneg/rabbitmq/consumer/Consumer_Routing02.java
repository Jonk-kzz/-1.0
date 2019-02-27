package com.xuehcneg.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 路由模式
 */
public class Consumer_Routing02 {

    //队列名称
    private static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    private static final String EXCHANGE_ROUTING_INFORM="exchange_routing_inform";

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
            connection = factory.newConnection();

            channel=connection.createChannel();
            /**
             * 交换机名称
             * 类型
             */
            channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT);

            /**
             * 队列名称
             * 是否持久化
             * 是否独占此队列
             * 不用是否删除此队列
             * 队列参数
             */
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);

            /**
             * 交换机跟队列绑定
             */                //队列名称            //交换机名称                //路由key
            channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_ROUTING_INFORM,QUEUE_INFORM_SMS);

            //消费方法
            DefaultConsumer defaultConsumer=new DefaultConsumer(channel){
                @Override
                public void handleDelivery(java.lang.String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    /*System.out.println("消息id: "+envelope.getDeliveryTag());

                    System.out.println("交换机名称:"+envelope.getExchange());

                    System.out.println("路由key: "+envelope.getRoutingKey());

                    System.out.println("是否在交付: "+envelope.isRedeliver());*/
                    final java.lang.String message = new java.lang.String(body,"UTF-8");

                    System.out.println(message);
                }
            };


            /**
             * 消息消费
             */
            //队列名称              //是否自动回复  //消费方法
            channel.basicConsume(QUEUE_INFORM_SMS,true,defaultConsumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
