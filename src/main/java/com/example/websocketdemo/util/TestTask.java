package com.example.websocketdemo.util;

import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

/**
 * @author Shiimin
 * 2020/9/28
 */
@Configuration
@EnableScheduling
public class TestTask {

//    @Scheduled(cron = "0/5 * * * * *")
    public void task() {
        WebSocket ws = new WebSocket();
        JSONObject jo = new JSONObject();
        jo.put("message", "这是后台返回的消息！");
        jo.put("To", "test");
//        ws.onMessage(jo.toString());
        System.out.println("信息已发送");
    }

}
