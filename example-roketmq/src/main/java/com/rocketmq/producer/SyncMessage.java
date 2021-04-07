package com.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author Auspice Tian
 * @time 2021-04-04 10:34
 * @current example-roketmq-com.rocketmq.message
 */
public class SyncMessage {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("test_cluster_group");
        producer.setNamesrvAddr("8.140.130.91:9876;8.140.130.91:9877");
        //producer.setNamesrvAddr("8.140.130.91:9876");
        producer.start();

        //设置超时重试次数
        //producer.setRetryTimesWhenSendFailed(3);

        String msgStr = "Cluster测试消息";

        /*
        * String topic, String tags, byte[] body
        * */
//        Message message = new Message("test_topic","CLUSTER",msgStr.getBytes("UTF-8"));
        Message message = new Message("test_cluster_topic","CLUSTER",msgStr.getBytes("UTF-8"));
        //SendResult result = producer.send(message,2000);
        SendResult result = producer.send(message);

        System.out.println(result);

        System.out.println("消息状态：" + result.getSendStatus());
        System.out.println("消息id：" + result.getMsgId());
        System.out.println("消息queue：" + result.getMessageQueue());
        System.out.println("消息offset：" + result.getQueueOffset());

        producer.shutdown();
    }
}
