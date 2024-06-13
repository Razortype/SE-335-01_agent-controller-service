package com.razortype.cyberproject.repository;

import com.razortype.cyberproject.entity.AttackJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AttackJobRepository extends JpaRepository<AttackJob, UUID> {

    // List<AttackJob> findAllByExecuteAtIsNullOrExecuteAtBeforeAndStartedFalse(LocalDateTime now);

    @Query("SELECT aj FROM AttackJob aj WHERE (aj.executeAt IS NULL OR aj.executeAt < :now) AND aj.started = FALSE")
    List<AttackJob> getAttackQueue(@Param("now") LocalDateTime now);

    List<AttackJob> findAllByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

}
