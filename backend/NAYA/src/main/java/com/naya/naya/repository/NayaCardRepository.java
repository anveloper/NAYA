package com.naya.naya.repository;

import com.naya.naya.entity.NayaCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NayaCardRepository extends JpaRepository<NayaCard, Long>                                                           {
    @Query("select n from NayaCard n where n.userId.userId = :userId")
    List<NayaCard> findByUserId(Long userId);
}
