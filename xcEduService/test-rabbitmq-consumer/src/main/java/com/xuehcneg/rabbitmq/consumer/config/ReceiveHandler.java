package com.xuehcneg.rabbitmq.consumer.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class ReceiveHandler {
    //队列名称
    public static final String QUEUE_INFORM_EMAIL = "inform_email_queue";
    public static final String QUEUE_INFORM_SMS = "inform_sms_queue";


    //监听email队列
    @RabbitListener(queues = {QUEUE_INFORM_EMAIL})
    public void receive_email(String msg, Message message, Channel channel){
        try {
            System.out.println(new String(message.getBody(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {QUEUE_INFORM_SMS})
    public void receive_sms(String msg,Message message,Channel channel){
        try {
            System.out.println(new String(message.getBody(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

}
