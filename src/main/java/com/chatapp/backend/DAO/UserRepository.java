package com.chatapp.backend.DAO;

import com.chatapp.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findOneByEmail(String email);

    List<User> findByEmail(String email);

    List<User> findByIdIn(List<Integer> ids);

    @Query(value = "select * from user where (upper(name) like %:search% or upper(email) like %:search%) and (id <> :id)",nativeQuery = true)
    List<User> searchUsers(String search,Integer id);
}
