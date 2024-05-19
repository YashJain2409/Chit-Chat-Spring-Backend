package com.chatapp.backend.socket;


import com.chatapp.backend.model.Chat;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.User;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SocketModule {

    Logger logger = LoggerFactory.getLogger(SocketModule.class);


    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(onConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("setup", User.class, setupUser());
        server.addEventListener("join room", String.class,joinRoom());
        server.addEventListener("leave room", String.class,leaveRoom());
        server.addEventListener("typing", Object.class,typeHandler());
        server.addEventListener("stop typing", String.class,stopTypeHandler());
        server.addEventListener("new message", Object.class,messageHandler());


    }

    private DataListener<Object> messageHandler() {
        return (senderClient,data,ackSender) -> {
            String jsonString = new Gson().toJson(data);
            JSONObject jsonObj = new JSONObject(jsonString);
            logger.info("new message recieved " + jsonObj);
            JSONArray arr = jsonObj.getJSONObject("chat").getJSONArray("users");
            int id = jsonObj.getJSONObject("sender").getInt("id");
            for(int i = 0;i < arr.length();i++){
                int currId = arr.getJSONObject(i).getInt("id");
                if(id == currId)
                    continue;
                senderClient.getNamespace().getRoomOperations(Integer.toString(currId)).sendEvent("message received",data);
            }
            System.out.println(arr.length());
        };
    }

    private DataListener<User> setupUser() {
        return (senderClient,data,ackSender) -> {
            logger.info("setting up user : " + data.getId());
            senderClient.joinRoom(Integer.toString(data.getId()));
            senderClient.sendEvent("connected");
        };
    }


    private DataListener<String> stopTypeHandler() {
        return (senderClient,data,ackSender) -> {
            logger.info("stop typing in room id : " + data);
            senderClient.getNamespace().getRoomOperations(data).sendEvent("stop typing");
        };
    }

    private DataListener<Object> typeHandler() {
        return (senderClient,data,ackSender) -> {
            logger.info("typing in room id : " + data);
            String jsonString = new Gson().toJson(data);
            JSONObject jsonObj = new JSONObject(jsonString);
            int chatId = jsonObj.getInt("chatId");
            int userId = jsonObj.getInt("userId");
            senderClient.getNamespace().getRoomOperations(Integer.toString(chatId)).sendEvent("typing",userId);
        };
    }


    private DataListener<String > joinRoom() {
        return (senderClient, data, ackSender) -> {
            logger.info("joined room " + data);
            senderClient.joinRoom(data);
//            socketService.saveMessage(senderClient, data);
        };
    }

    private DataListener<String> leaveRoom() {
        return (senderClient, data, ackSender) -> {
            logger.info("leaved room " + data);
            senderClient.leaveRoom(data);
//            socketService.saveMessage(senderClient, data);
        };
    }


    private ConnectListener onConnected() {
        return (client) -> {
            logger.info("Socket ID[{}]  Connected to socket", client.getSessionId().toString());
        };

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            logger.info("Socket ID[{}]  disconnected", client.getSessionId().toString());
        };
    }


}
