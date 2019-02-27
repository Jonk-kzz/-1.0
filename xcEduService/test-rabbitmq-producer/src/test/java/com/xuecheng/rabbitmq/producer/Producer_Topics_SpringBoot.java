package com.xuecheng.rabbitmq.producer;

import com.xuecheng.test.rabbitmq.ApplicationRabbit;
import com.xuecheng.test.rabbitmq.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ApplicationRabbit.class)
@RunWith(SpringRunner.class)
public class Producer_Topics_SpringBoot {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void produces01(){
        for (int i=1;i<=5;i++){
            String message="sms email inform to user"+i;
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM,"inform.sms.email",message.getBytes());
            System.out.println("Send message is : ' "+ message+" ' ");
        }
    }
}
