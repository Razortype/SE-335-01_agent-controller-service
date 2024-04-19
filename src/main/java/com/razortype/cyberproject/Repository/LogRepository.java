package com.razortype.cyberproject.Repository;

import com.razortype.cyberproject.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {
}
