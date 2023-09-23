package org.tinycode.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ExampleRocketMQ {

    @Test
    public void producer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ooxx-banch");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        List<Message> mqs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("aabb");
            message.setTags("bangch-a");
            message.setKeys("key" + i);
            message.setBody(("hellow" + i).getBytes(StandardCharsets.UTF_8));
            mqs.add(message);
        }
        //批量发送
        SendResult sendResult = producer.send(mqs);
        System.out.println(sendResult);
    }

    /**
     * 生产消费无序消费  -重新消费的场景
     * <p>
     * messageDelayLevel = 1s 5s 10s 30s 1m 2m 3m 4m
     * 18个延迟级别
     * 返回 重试
     * ConsumeConcurrentlyStatus.RECONSUME_LATER
     * 设置重试次数
     * consumer.setMaxReconsumeTimes(2);
     * 延迟队列
     * %DLQ%aabb-banch-2
     * 重试队列
     * %RETRY%aabb-banch-2
     * 总结  rocketmq 维护了 重试队列 、死信队列  以topic形式且 以消费者组作为后缀
     * 无序的时候使用场景
     */
    @Test
    public void consumerPush() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("aabb-banch-2");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("aabb", "*");
        //设置最大重试次数为2
        consumer.setMaxReconsumeTimes(2);
        //每次默认拉去32条 但是 只返回给接口 1
//        consumer.setConsumeMessageBatchMaxSize(1);
        //每次只拉去一条  （虽然是push 但是实际上还是通过 不断的拉去实现的push）
        consumer.setPullBatchSize(1);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = list.get(0);
                System.out.println(new String(messageExt.getBody()));
                if (messageExt.getKeys().equals("key1")) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
}
