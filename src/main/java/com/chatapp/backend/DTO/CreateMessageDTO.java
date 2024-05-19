package com.chatapp.backend.DTO;

public class CreateMessageDTO {
    private int chatId;
    private String content;

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CreateMessageDTO{" +
                "chatId=" + chatId +
                ", content='" + content + '\'' +
                '}';
    }
}
