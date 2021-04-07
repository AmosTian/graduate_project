package com.rocketmq.spring;

import com.rocketmq.spring.transaction.TransactionProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Auspice Tian
 * @time 2021-04-06 14:49
 * @current example-roketmq-com.rocketmq.spring
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSpringRocketMQ {

    @Autowired
    SpringProducer producer;

    @Autowired
    TransactionProducer transactionProducer;

    @Test
    public void testSendMsg(){
        String msg = "第三个Spring RocketMq 消息";

        this.producer.sendMsg("test_spring_topic",msg);
        System.out.println("发送成功!");
    }

    @Test
    public void testSendTransactionMsg() throws InterruptedException {
        String msg = "消息回查测试2!";

        this.transactionProducer.sendMsg("test_spring_transaction_topic",msg);

        Thread.sleep(9999999L);
    }

}
