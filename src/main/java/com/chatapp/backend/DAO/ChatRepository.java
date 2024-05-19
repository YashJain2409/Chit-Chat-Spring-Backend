package com.chatapp.backend.DAO;

import com.chatapp.backend.model.Chat;
import com.chatapp.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Integer> {

    @Query(value = "select id,is_group_chat,latest_message_id,created_on,chat_name,group_admin_id from chat_users cu1 inner join chat_users cu2 on (cu1.chat_id = cu2.chat_id and cu1.users_id = :i and cu2.users_id = :userId) inner join chat on chat.id = cu1.chat_id where is_group_chat = 'false'",nativeQuery = true)
    List<Chat> findChatIfExistsByUserId(int i, int userId);

    List<Chat> findByUsersOrderByCreatedOnDesc(User user);

}
