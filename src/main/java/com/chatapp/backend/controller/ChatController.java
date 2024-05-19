package com.chatapp.backend.controller;

import com.chatapp.backend.DAO.ChatRepository;
import com.chatapp.backend.DTO.AccessChatDTO;
import com.chatapp.backend.DTO.CreateGroupChatDTO;
import com.chatapp.backend.DTO.RenameChatDTO;
import com.chatapp.backend.model.Chat;
import com.chatapp.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    ChatService chatService;

    @PostMapping
    public ResponseEntity<Chat> accessChat(@RequestBody AccessChatDTO accessChatDTO) {
        return chatService.accessChat(accessChatDTO.getUserId());
    }

    @GetMapping
    public ResponseEntity<List<Chat>> fetchChats() {
        return chatService.fetchChats();
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChat(@RequestBody CreateGroupChatDTO createGroupChatDTO) {return chatService.createGroupChat(createGroupChatDTO);}

    @PutMapping("/rename")
    public ResponseEntity<Chat> renameGroup(@RequestBody RenameChatDTO renameChatDTO) {
        return chatService.renameGroup(renameChatDTO);
    }

    @PutMapping("/groupadd")
    public ResponseEntity<Chat> addToGroup(@RequestBody Map<String,Object> addToGroupBody) {
        return chatService.addToGroup(addToGroupBody);
    }

    @PutMapping("/groupremove")
    public ResponseEntity<Chat> removeFromGroup(@RequestBody Map<String,Object> removeFromGroupBody){
        return chatService.removeFromGroup(removeFromGroupBody);
    }

}
