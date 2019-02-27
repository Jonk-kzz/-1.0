package com.xuehcneg.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 工作队列 消费者
 */
public class Consumer01 {

    private static final String QUEUE="helloWorld";
    public static void main(String[] args) {
        final ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ所在服务器的IP和端口
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
         Connection connection =null;
         Channel channel=null;
         try{
             connection= factory.newConnection();

             channel = connection.createChannel();
             /**
              * 声明队列,如果RabbitMQ中没有此队列将自动创建
              *  param1:队列名称
              *  param2:是否持久化
              *  param3:队列是否独立此连接
              *  param4:队列不再使用是否自动删除此队列
              *  param5:队列参数
              */
             channel.queueDeclare(QUEUE,true,false,false,null);


             //定义消费方法
             DefaultConsumer consumer=new DefaultConsumer(channel){
                 /**
                  * 消费者接收消息调用此方法
                  * @param consumerTag 消费者的标签, 在channel.basicConsumer()去指定
                  * @param envelope 消息包含的内容,可从中获取消息id,消息routingKey,交换机,消息和重传标志(收到消息失败后是否需要重新发送)
                  * @param properties
                  * @param body 报文
                  * @throws IOException
                  */
                 @Override
                 public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    /* super.handleDelivery(consumerTag, envelope, properties, body);*/
                     //交换机
                     final String exchange = envelope.getExchange();
                     //路由key
                     final String routingKey = envelope.getRoutingKey();
                     //消息id
                     final long deliveryTag = envelope.getDeliveryTag();
                     //消息内容
                     final String msg = new String(body);
                     System.out.println("receive message.."+msg);
                 }
             };

             /**
              * 监听队列String queue ,boolean autoAck, Consumer callback
              * 1.参数明细
              * 2.是否自动回复,设置为true表示消息接收到自动向mq回复接收到了,mq接收到回复会删除消息,设置为false则需要手动回复
              * 3.消费消息方法,消费者接收到消息后调用此方法
              */
             channel.basicConsume(QUEUE,true,consumer);
         }catch(Exception e){
             e.printStackTrace();
         }

    }
}
