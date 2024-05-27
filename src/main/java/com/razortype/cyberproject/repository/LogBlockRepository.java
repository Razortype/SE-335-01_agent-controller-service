package com.razortype.cyberproject.repository;

import com.razortype.cyberproject.entity.AttackJob;
import com.razortype.cyberproject.entity.LogBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LogBlockRepository extends JpaRepository<LogBlock, UUID> {

    Optional<LogBlock> findByAttackJob(AttackJob attackJob);

}
