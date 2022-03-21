package com.example.secondkill.utils.rocketmq;

//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
///**
// * @author zjl(1552217634 @ qq.com)
// * @date 2022/3/20 19:28
// */
//@Component
//public class MsgProducer {
//
//
//    /**
//     * 生产者的组名
//     */
//    @Value("${apache.rocketmq.producer.producerGroup}")
//    private String producerGroup;
//
//    /**
//     * NameServer 地址
//     */
//    @Value("${apache.rocketmq.namesrvAddr}")
//    private String nameserverAddr;
//
//    //默认生产者
//    private DefaultMQProducer producer;
//
//    public DefaultMQProducer getProducer() {
//        return producer;
//    }
//
//    @PostConstruct
//    public void init() {
//        //生产者对象
//        producer =new DefaultMQProducer(producerGroup);
//        //指定NameServer地址，多个地址以 ; 隔开
//        //如 producer.setNamesrvAddr("192.168.100.141:9876;192.168.100.142:9876;192.168.100.149:9876");
//
//        //名称服务
//        producer.setNamesrvAddr(nameserverAddr);
//
//        producer.setVipChannelEnabled(false);
//        try {
//            /**
//             * Producer对象在使用之前必须要调用start初始化，只能初始化一次
//             */
//            producer.start();
//        }catch (Exception ex){
//
//        }
//        // producer.shutdown();  一般在应用上下文，关闭的时候进行关闭，用上下文监听器
//    }
//}
