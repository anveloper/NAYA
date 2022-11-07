package com.naya.naya.repository;

import com.naya.naya.entity.SendCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SendCardRepository extends JpaRepository<SendCard,Long> {

    SendCard save(SendCard sendCard);

    SendCard findBySendCardIdAndUserId(String userId, long sendCardId);
}
