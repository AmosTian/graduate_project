package com.rocketmq.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author Auspice Tian
 * @time 2021-04-04 16:59
 * @current example-roketmq-com.rocketmq.order
 */
public class OrderProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("test_order_group");
        producer.setNamesrvAddr("8.140.130.91:9876");
        producer.start();

        for (int i = 0; i <= 90; i++) {

            int orderId = i % 10;
            //生产10个订单的消息,每个订单9条消息
            String msgStr = "orderId-->" + orderId;

            Message message = new Message("test_order_topic","ORDER_MSG",msgStr.getBytes("UTF-8"));
            /*
            * public SendResult send(Message msg, MessageQueueSelector selector, Object arg)
            * MessageQueue select(final List<MessageQueue> mqs, final Message msg, final Object arg);
            * */
            SendResult sendResult = producer.send(
                    message,
                    (mqs, msg, arg) -> {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        return mqs.get(index);
                    },
                    orderId);
            System.out.println(sendResult);
        }
        producer.shutdown();
    }
}
