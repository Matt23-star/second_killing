//package com.example.secondkill.utils.rocketmq;
//
//import com.example.secondkill.utils.ResultUtils;
//import org.apache.rocketmq.client.exception.MQBrokerException;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.remoting.common.RemotingHelper;
//import org.apache.rocketmq.remoting.exception.RemotingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.UnsupportedEncodingException;
//
///**
// * @author zjl(1552217634 @ qq.com)
// * @date 2022/3/20 19:37
// */
//@Service
//public class MQService {
//
//    @Autowired
//    private MsgProducer producer;
//
//    public Object send(String msg) throws UnsupportedEncodingException, InterruptedException, RemotingException, MQBrokerException, MQClientException {
//
//        // 创建一个消息实例，包含 topic、tag 和 消息体
//        Message message=new Message("testTopic","",msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
//
//        //调用方法==>返回消息体
//        SendResult result =producer.getProducer().send(message);
//
//        System.out.println("发送响应消息ID:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
//
//        return ResultUtils.success();
//    }
//
//}
