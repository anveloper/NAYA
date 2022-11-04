package com.naya.naya.repository;

import com.naya.naya.entity.Design;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface DesignRepository extends JpaRepository<Design,Long> {

    @Query("select n from Design n where n.nayaCardId.nayaCardId = :nayaCardId")
    List<Design> findByNayaCardId(Long nayaCardId);

    @Modifying
    @Transactional
    @Query("delete from Design n where n.nayaCardId.nayaCardId = :nayaCardId")
    void deleteByNayaCardId(Long nayaCardId);
}
