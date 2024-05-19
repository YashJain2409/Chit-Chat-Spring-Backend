package com.chatapp.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;

    private Date createdOn;

    @ManyToOne
    private User sender;

    @ManyToOne
    @JsonIgnoreProperties("message")
    private Chat chat;

    public Message() {

    }

    public Message(int id, String content, Date createdOn, User sender, Chat chat) {
        this.id = id;
        this.content = content;
        this.createdOn = createdOn;
        this.sender = sender;
        this.chat = chat;
    }

    public Message(String content, Date createdOn) {
        this.content = content;
        this.createdOn = createdOn;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

}
