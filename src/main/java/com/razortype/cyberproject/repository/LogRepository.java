package com.razortype.cyberproject.repository;

import com.razortype.cyberproject.entity.Log;
import com.razortype.cyberproject.entity.LogBlock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {
    Page<Log> findByLogBlock(LogBlock logBlock, Pageable pageable);
    @Query("SELECT COUNT(l) FROM Log l WHERE l.logBlock.id = :logBlockId")
    long countByLogBlockId(@Param("logBlockId") UUID logBlockId);
}
