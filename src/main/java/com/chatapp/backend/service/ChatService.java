package com.chatapp.backend.service;

import com.chatapp.backend.DAO.ChatRepository;
import com.chatapp.backend.DAO.UserRepository;
import com.chatapp.backend.DTO.CreateGroupChatDTO;
import com.chatapp.backend.DTO.RenameChatDTO;
import com.chatapp.backend.exception.ApplicationException;
import com.chatapp.backend.model.Chat;
import com.chatapp.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatRepository chatDAO;
    public ResponseEntity<Chat> accessChat(int userId) {
        User loggedInUser = userService.getCurrentUser().getBody();
        User friend = userRepository.findById(userId).get();
        Chat c = null;
        List<Chat> existChat = chatDAO.findChatIfExistsByUserId(loggedInUser.getId(),userId);
        if(!existChat.isEmpty())
            c = existChat.get(0);
        if(c != null) return new ResponseEntity(c,HttpStatus.OK) ;
        Chat chat = new Chat("sender",false, Instant.now());
        chat.addUser(loggedInUser);
        chat.addUser(friend);
        try {
            c = chatDAO.save(chat);
        }catch (Exception e){
            throw new ApplicationException(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(c,HttpStatus.CREATED);
    }

    public ResponseEntity<List<Chat>> fetchChats() {
        User user = userService.getCurrentUser().getBody();
        List<Chat> chats = chatDAO.findByUsersOrderByCreatedOnDesc(user);
        return new ResponseEntity<>(chats,HttpStatus.OK);
    }

    public ResponseEntity<Chat> createGroupChat(CreateGroupChatDTO createGroupChatDTO) {
        User user = userService.getCurrentUser().getBody();
        List<Integer> userIds = createGroupChatDTO.getUserIds();
        List<User> users = userRepository.findByIdIn(userIds);
        if(users.size() < 2)
            throw new ApplicationException("More than 2 users are required to form a group chat",HttpStatus.BAD_REQUEST);
        users.add(user);
        Chat groupChat = new Chat(createGroupChatDTO.getName(),true,users,user,Instant.now());
        chatDAO.save(groupChat);
        return new ResponseEntity<>(groupChat,HttpStatus.CREATED);
    }

    public ResponseEntity<Chat> renameGroup(RenameChatDTO renameChatDTO) {
        int chatId = renameChatDTO.getChatId();

        Optional<Chat> optionalChatToBeRenamed;
        try {
            optionalChatToBeRenamed = chatDAO.findById(chatId);
        }catch (Exception e){
            throw new ApplicationException("Internal server error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(!optionalChatToBeRenamed.isPresent()){
            throw new ApplicationException("chat not found",HttpStatus.NOT_FOUND);
        }
        Chat chatToBeRenamed = optionalChatToBeRenamed.get();
        chatToBeRenamed.setChatName(renameChatDTO.getChatName());
        chatToBeRenamed.setCreatedOn(Instant.now());
        Chat updatedChat = chatDAO.save(chatToBeRenamed);
        return new ResponseEntity<>(updatedChat,HttpStatus.OK);
    }

    public ResponseEntity<Chat> addToGroup(Map<String, Object> addUserObj) {
        int chatId = (Integer)addUserObj.get("chatId");
        int userId = (Integer)addUserObj.get("userId");
        User userToBeAdded = userRepository.findById(userId).get();
        Chat chatForUser = chatDAO.findById(chatId).get();
        List<User> existingUsers = chatForUser.getUsers();
        existingUsers.add(userToBeAdded);
        chatForUser.setUsers(existingUsers);
        chatForUser.setCreatedOn(Instant.now());
        Chat updateChat = chatDAO.save(chatForUser);
        return new ResponseEntity<>(updateChat,HttpStatus.OK);
    }


    public ResponseEntity<Chat> removeFromGroup(Map<String, Object> removeFromGroupBody) {
        int chatId = (Integer)removeFromGroupBody.get("chatId");
        int userId = (Integer)removeFromGroupBody.get("userId");
        User userToBeRemoved = userRepository.findById(userId).get();
        Chat chatForUser = chatDAO.findById(chatId).get();
        List<User> existingUsers = chatForUser.getUsers();
        existingUsers.remove(userToBeRemoved);
        chatForUser.setUsers(existingUsers);
        chatForUser.setCreatedOn(Instant.now());
        Chat updatedChat = chatDAO.save(chatForUser);
        return new ResponseEntity<>(updatedChat,HttpStatus.OK);
    }
}
