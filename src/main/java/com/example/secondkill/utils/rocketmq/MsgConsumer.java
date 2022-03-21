package com.example.secondkill.utils.rocketmq;

//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.annotation.PostConstruct;
//
///**
// * @author zjl(1552217634 @ qq.com)
// * @date 2022/3/20 19:28
// */
//public class MsgConsumer {
//    /**
//     * 生产者的组名
//     */
//    @Value("${apache.rocketmq.consumer.PushConsumer}")
//    private String consumerGroup;
//
//    /**
//     * NameServer 地址
//     */
//    @Value("${apache.rocketmq.namesrvAddr}")
//    private String nameserverAddr;
//
//
//    @PostConstruct
//    public void defaultMQPushConsumer() {
//        //消费者的组名
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
//
//        //指定NameServer地址
//        consumer.setNamesrvAddr(nameserverAddr);
//        try {
//
//            //也可以指定tag进行消息过滤
//            consumer.subscribe("testTopic", "*");
//
//            //CONSUME_FROM_LAST_OFFSET 默认策略，从该队列最尾开始消费，跳过历史消息
//            //CONSUME_FROM_FIRST_OFFSET 从队列最开始开始消费，即历史消息（还储存在broker的）全部消费一遍
//            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
//
//            //MessageListenerOrderly 这个是有序的(订单就必须有顺序的)
//            //MessageListenerConcurrently 这个是无序的,并行的方式处理，效率高很多
//            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
//                try {
//                    //遍历消息
//                    for (MessageExt messageExt : list) {
//
//                        System.out.println("messageExt: " + messageExt);//输出消息内容
//
//                        //获得消息内容
//                        String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
//
//                        System.out.println("消费响应：msgId : " + messageExt.getMsgId() + ",  msgBody : " + messageBody);//输出消息内容
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return ConsumeConcurrentlyStatus.RECONSUME_LATER; //稍后再试
//                }
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
//            });
//            consumer.start();   //开启监听consumer
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
