package com.example.secondkill.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.secondkill.entity.pojo.UserKillState;
//import com.example.secondkill.utils.rocketmq.MQService;
//import org.apache.rocketmq.client.exception.MQBrokerException;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.remoting.exception.RemotingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.UnsupportedEncodingException;
//import java.util.UUID;
//
///**
// * @author zjl(1552217634 @ qq.com)
// * @date 2022/3/20 19:46
// */
//@RestController
//public class MQController {
//
//    @Autowired
//    private MQService mqService;
//
//
//    @RequestMapping("/mq/testSend")
//    public String mqTestSend(){
//        UserKillState userKillState = new UserKillState();
//        userKillState.setId(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase().substring(0, 32));
//        userKillState.setUserId("2618271127218");
//        userKillState.setKillActivityId("57845445");
//        userKillState.setState("购买成功");
//        Object result = null;
//        try {
//            result = mqService.send(JSONObject.toJSONString(userKillState));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (RemotingException e) {
//            e.printStackTrace();
//        } catch (MQBrokerException e) {
//            e.printStackTrace();
//        } catch (MQClientException e) {
//            e.printStackTrace();
//        }
//        return JSONObject.toJSONString(result);
//    }
//}
