package org.tinycode.rocketmq;

import lombok.SneakyThrows;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TransactionRocketMQ {

    private String produceGroup = "produceMq7";
    private String consumesGroup = "consumesMq7";
    private String topic = "transactionTopic7";

    /**
     * 事务
     */
    @Test
    public void transactionalProducer() throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer(produceGroup);
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setTransactionListener(new TransactionListener() {
            //half 半消息成功了才能执行本地事务 也需要监听
            @SneakyThrows
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                /*
                 * 三个地方可以传导信息
                 * message body 中传递的消息 （1，占用网络带宽 2、污染body）
                 * userProperty 也是通过网络传递给consumer（占用网络带宽）    message.getProperty("type")
                 * 通过args 传递 （最优解）   String type = (String) o;
                 */
                String type = (String) o;
                String transactionId = message.getTransactionId();

                /*
                 * 状态有2个
                 * mq 的half 半消息，这个状态驱动rocketMq 会查producer
                 * server 应该是无状态的应该吧 transactionId 随着事务的执行写入事件执行表
                 */
                switch (type) {
                    case "0":
                        System.out.println(Thread.currentThread().getName() + "- type：" + type);
                        System.out.println("根据事务id 检查事务是否成功：" + transactionId);
                        //UNKNOW 都会走checkLocalTransaction（）进行监听
                        return LocalTransactionState.UNKNOW;
                    case "1":
                        System.out.println(Thread.currentThread().getName() + "- type：" + type);
                        System.out.println("根据事务id 检查事务是否成功：" + transactionId);
                        //这种状态 consumer 是消费不到这种消息的
                        return LocalTransactionState.ROLLBACK_MESSAGE;
                    case "2":
                        System.out.println(Thread.currentThread().getName() + "- type：" + type);
                        System.out.println("根据事务id 检查事务是否成功：" + transactionId);
                        Thread.sleep(1000 * 20);
                        //等待时间过长也会走 checkLocalTransaction 进行回查
                        return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }

            //半消息的回调要被监听
            @SneakyThrows
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                String type = messageExt.getProperty("type");
                String transactionId = messageExt.getTransactionId();
                switch (type) {
                    case "0":
                        System.out.println(new String(messageExt.getBody()) + "type:" + type);
                        //半消息检查之后的状态返回UNKNOW  回调service
                        Thread.sleep(3000);
                        System.out.println("监听办消息回调 type = 0, 根据事务id查询事务是否成- transactionId：" + transactionId);
                        return LocalTransactionState.COMMIT_MESSAGE;
                    case "1":
                        //如果有实现窗口的话也会进入到该选择
                        System.out.println(new String(messageExt.getBody()) + "type:" + type);
                        System.out.println("监听办消息回调 type = 1, 根据事务id查询事务是否成- transactionId：" + transactionId);
                        return LocalTransactionState.UNKNOW;
                    case "2":
                        System.out.println(new String(messageExt.getBody()) + "type:" + type);
                        System.out.println("监听办消息回调 type = 2, 根据事务id查询事务是否成- transactionId：" + transactionId);
                        return LocalTransactionState.COMMIT_MESSAGE;
                }
                System.out.println(Thread.currentThread().getName());
                return null;
            }
        });

        producer.setExecutorService(new ThreadPoolExecutor(
                1,
                Runtime.getRuntime().availableProcessors(),
                2000,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(2000),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "Transaction thread");
                    }
                }
        ));


        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message(topic,
                    "tags",
                    "keys" + i,
                    ("message" + i).getBytes(StandardCharsets.UTF_8));
            message.putUserProperty("type", i % 3 + "");
            producer.sendMessageInTransaction(message, i % 3 + "");
        }

        System.in.read();
    }

    @Test
    public void transactionalConsume() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumesGroup);
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(topic, "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(msg -> {
                    String type = msg.getProperty("type");
                    System.out.println(new String(msg.getBody()) + "type:" + type);
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }
}
