package com.chatapp.backend.DAO;

import com.chatapp.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
    List<Message> findBychatId(int chatId);
}
