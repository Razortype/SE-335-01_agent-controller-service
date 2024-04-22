package com.razortype.cyberproject.Repository;

import com.razortype.cyberproject.entity.Log;
import com.razortype.cyberproject.entity.LogBlock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {
    Page<Log> findByLogBlock(LogBlock logBlock, Pageable pageable);
}
