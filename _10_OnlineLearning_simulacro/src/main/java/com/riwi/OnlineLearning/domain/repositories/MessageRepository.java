package com.riwi.OnlineLearning.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.OnlineLearning.domain.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> 
{
    List<Message> findBySenderId(Long id);
    List<Message> findByReceiverId(Long id);
    List<Message> findByCourseId(Long id);

    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
