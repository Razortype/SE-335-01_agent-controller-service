package com.razortype.cyberproject.repository;

import com.razortype.cyberproject.entity.AttackJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AttackJobRepository extends JpaRepository<AttackJob, UUID> {

    List<AttackJob> findAllByExecuteAtIsNullOrExecuteAtBeforeAndStartedFalse(LocalDateTime now);

}
