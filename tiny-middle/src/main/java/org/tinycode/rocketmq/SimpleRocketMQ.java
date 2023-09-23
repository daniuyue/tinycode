package org.tinycode.rocketmq;


import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimpleRocketMQ {

    @Test
    public void producer() throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("ooxx");
        producer.setNamesrvAddr("10.33.36.189:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message();
            message.setTopic("ooxx-02");
            message.setTags("A");
            message.setBody(("hellow" + i).getBytes(StandardCharsets.UTF_8));
            //确保Store收到信息后再返回
            message.setWaitStoreMsgOK(true);

            //第一种发送方式 同步发送
//            SendResult result = producer.send(message);
//            System.out.println(result);

            //第二种发送方式 异步返回
//            producer.send(message, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    System.out.println(sendResult);
//                }

//                @Override
//                public void onException(Throwable throwable) {

//                }
//            });
            //第三种发送方式 没有返回
            //producer.sendOneway(message);
            //第四种发送方式 制定推送队列  broker-a是默认borker的名称

            MessageQueue messageQueue = new MessageQueue("ooxx-02", "broker-a", 0);
            SendResult sendResult = producer.send(message, messageQueue);
            System.out.println(sendResult);
        }
    }

    @Test
    public void consumerPull() throws Exception {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("consumes_ox_1");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.start();
        Collection<MessageQueue> messageQueues = consumer.fetchMessageQueues("ooxx-01");

        //拿到数据的queue队列
        System.out.println("queues");
        messageQueues.forEach(System.out::println);

        //选择拉去的所有的消息队列
//        consumer.assign(messageQueues);

        //手动的选择queue 队列
        Collection<MessageQueue> mqs = new ArrayList<>();
        MessageQueue messageQueue = new MessageQueue("ooxx-01", "broker-a", 0);
        mqs.add(messageQueue);
        consumer.assign(mqs);

        //seek  指定某个队列的偏移量
        consumer.seek(messageQueue, 2);

        System.out.println("poll");
        List<MessageExt> poll = consumer.poll();

        poll.forEach(msg -> {
            System.out.println(new String(msg.getBody()));
        });
        System.in.read();
    }

    @Test
    public void consumerPush() throws Exception {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumes_ox");
        //指定nameservice地址
        consumer.setNamesrvAddr("127.0.0.1:9876");
        //指定消费方式
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("ooxx-01", "*");
        //通过回调的方式监听消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(msg -> {
                    System.out.println(new String(msg.getBody()));
                    System.out.println(msg.getProperties());
                    System.out.println("--------------------");
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }

    @Test
    public void admin() throws Exception {
        //admin 获取rocketmq的元数据
        DefaultMQAdminExt adminExt = new DefaultMQAdminExt();
        adminExt.setNamesrvAddr("127.0.0.1:9876");
        adminExt.start();

        //获取 topicList 的元数据信息
        TopicList topicList = adminExt.fetchAllTopicList();
        topicList.getTopicList().forEach(System.out::println);
//        System.out.println(adminExt.examineTopicRouteInfo("ooxx-01"));
    }
}
