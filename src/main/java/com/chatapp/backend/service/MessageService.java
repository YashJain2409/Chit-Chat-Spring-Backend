package com.chatapp.backend.service;

import com.chatapp.backend.DAO.ChatRepository;
import com.chatapp.backend.DAO.MessageRepository;
import com.chatapp.backend.DAO.UserRepository;
import com.chatapp.backend.DTO.CreateMessageDTO;
import com.chatapp.backend.exception.ApplicationException;
import com.chatapp.backend.model.Chat;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    UserService userService;

    @Autowired
    ChatRepository chatDAO;

    @Autowired
    MessageRepository messageDAO;

    public ResponseEntity<Message> sendMessage(CreateMessageDTO createMessageDTO) {
        User u = userService.getCurrentUser().getBody();
        Chat c = null;
        try {
            c = chatDAO.findById(createMessageDTO.getChatId()).get();
        }catch (Exception e){
            throw new ApplicationException("could not find chat",HttpStatus.BAD_REQUEST);
        }
        Message m = new Message(createMessageDTO.getContent(), new Date());
        m.setSender(u);
        m.setChat(c);
        try {
            messageDAO.save(m);
        }catch (Exception e){
            throw new ApplicationException("Could not save message",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        c.setCreatedOn(Instant.now());
        c.setLatestMessage(m);
        chatDAO.save(c);
        return new ResponseEntity<>(m, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Message>> getAllMessage(int chatId) {
        List<Message> messages = messageDAO.findBychatId(chatId);
        return new ResponseEntity<>(messages,HttpStatus.OK);
    }
}
