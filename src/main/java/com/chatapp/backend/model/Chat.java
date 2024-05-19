package com.chatapp.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String chatName;
    private boolean isGroupChat;

    @ManyToMany
    private List<User> users;

    @OneToOne
    @JsonIgnoreProperties("chat")
    private Message latestMessage;

    @ManyToOne
    private User groupAdmin;

    private Instant createdOn;

    public Chat(){

    }

    public Chat(int id, String chatName, boolean isGroupChat, List<User> users, Message latestMessage, User groupAdmin, Instant createdOn) {
        this.id = id;
        this.chatName = chatName;
        this.isGroupChat = isGroupChat;
        this.users = users;
        this.latestMessage = latestMessage;
        this.groupAdmin = groupAdmin;
        this.createdOn = createdOn;
    }

    public Chat(String chatName, boolean isGroupChat, List<User> users, User groupAdmin, Instant createdOn) {
        this.chatName = chatName;
        this.isGroupChat = isGroupChat;
        this.users = users;
        this.groupAdmin = groupAdmin;
        this.createdOn = createdOn;
    }

    public Chat(String chatName, boolean isGroupChat, Instant createdOn) {
        this.chatName = chatName;
        this.isGroupChat = isGroupChat;
        this.createdOn = createdOn;
    }

    public void addUser(User u){
        if(users == null)
            users = new ArrayList<>();
        users.add(u);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    @JsonProperty(value = "isGroupChat")
    public boolean isGroupChat() {
        return isGroupChat;
    }

    public void setGroupChat(boolean groupChat) {
        isGroupChat = groupChat;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Message getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(Message latestMessage) {
        this.latestMessage = latestMessage;
    }

    public User getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(User groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }
}
