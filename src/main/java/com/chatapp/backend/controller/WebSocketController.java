package com.chatapp.backend.controller;

import com.chatapp.backend.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chats")
    public Message recieveMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getChat().getId()),"/chats",message);
        return message;
    }

}
