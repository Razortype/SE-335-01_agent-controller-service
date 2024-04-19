package com.razortype.cyberproject.Repository;

import com.razortype.cyberproject.entity.AttackJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AttackJobRepository extends JpaRepository<AttackJob, UUID> {

}
