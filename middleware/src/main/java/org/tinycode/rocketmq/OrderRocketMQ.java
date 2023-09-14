package org.tinycode.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.jupiter.api.Test;

import java.util.List;

public class OrderRocketMQ {

    /**
     * Producer
     * 保证有序性
     * 自定义 队列选择器
     * <p>
     * Consumer
     * 使用
     * new MessageListenerOrderly()
     * <p>
     * 总结 生产的时候保证有顺序依赖的消息进入同一个queue，消费的时候也会在多线程的情况下保证单个队列有序性
     * 如果只创建一个queue 就是全局有序
     */
    @Test
    public void orderProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("order-banchs");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("order-00", "order_tag", ("order" + i + "type" + i % 3).getBytes());
            SendResult send = producer.send(message, new MessageQueueSelector() {  //new 选择器 有没有性能问题
                /**
                 * 会出现 不稳定因素
                 * 比例queue的大小改变的时候  指定的的队列就会发生变化
                 */
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    Integer queueIndex = (Integer) arg;
                    int target = queueIndex % list.size();
                    //思想不同
//                    return list.get(queueIndex)
                    return list.get(target);
                }
            }, i % 3);

            System.out.println(send);
        }
    }


    /**
     * 有序消费
     */
    @Test
    public void orderConsumerPush() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("aabb-banch-2");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("order-00", "*");

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {

                for (MessageExt messageExt : list) {
                    System.out.println(Thread.currentThread().getName() + new String(messageExt.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }


}
