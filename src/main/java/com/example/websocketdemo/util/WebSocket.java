package com.example.websocketdemo.util;

import com.example.websocketdemo.dto.UserMessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shiimin
 * 2020/9/28
 */
@Component
@ServerEndpoint("/webSocket/{username}")
public class WebSocket {

    private static int onlineCount = 0;
    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    private Session session;
    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session){
        this.username = username;
        this.session = session;
        addOnlineCount();
        clients.put(username, session);
        System.out.println(username+"已连接");

//        WebSocket ws = new WebSocket();
//        JSONObject jo = new JSONObject();
//        jo.put("message", "这是后台返回的消息！");
//        jo.put("To",invIO.getIoEmployeeUid());
//        ws.onMessage(jo.toString());
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session){
        clients.remove(username);
        subOnlineCount();
        System.out.println(username+"已断开连接");
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        sendMessageAll(message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessageTo(String message, String To){
        // session.getBasicRemote().sendText(message);
        //session.getAsyncRemote().sendText(message);
        Session session = clients.get(To);
        session.getAsyncRemote().sendText(message);
    }

    public void sendMessageAll(String message) throws IOException {
        for (Session session : clients.values()) {
            UserMessageDTO userMessageDTO = new UserMessageDTO();
            userMessageDTO.setUsername(username);
            userMessageDTO.setMessage(message);
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(userMessageDTO);
            session.getAsyncRemote().sendText(s);

        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

    public static synchronized Map<String, Session> getClients() {
        return clients;
    }

}
