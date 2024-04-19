package com.razortype.cyberproject.Repository;

import com.razortype.cyberproject.entity.LogBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogBlockRepository extends JpaRepository<LogBlock, UUID> {
}
