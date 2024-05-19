package com.chatapp.backend.controller;

import com.chatapp.backend.DAO.ChatRepository;
import com.chatapp.backend.DAO.MessageRepository;
import com.chatapp.backend.DAO.UserRepository;
import com.chatapp.backend.DTO.CreateMessageDTO;
import com.chatapp.backend.model.Chat;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.User;
import com.chatapp.backend.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(HttpServletRequest req, @RequestBody CreateMessageDTO createMessageDTO){
        return messageService.sendMessage(createMessageDTO);

    }

    @GetMapping("{chatId}")
    public ResponseEntity<List<Message>> allMessages(@PathVariable int chatId) {
        return messageService.getAllMessage(chatId);
    }
}
