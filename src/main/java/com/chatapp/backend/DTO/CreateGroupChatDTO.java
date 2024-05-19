package com.chatapp.backend.DTO;

import com.chatapp.backend.model.User;

import java.util.List;

public class CreateGroupChatDTO {
    private  String name;

    private List<Integer> userIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUsers(List<Integer> users) {
        this.userIds = userIds;
    }
}
