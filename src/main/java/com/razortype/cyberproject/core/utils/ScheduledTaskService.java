package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.service.abstracts.AttackJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledTaskService {

    private final AttackJobService attackJobService;

    @Scheduled(fixedRate = 5000)
    public void executeJobTask() {

        // List<AttackJob> jobs = attackJobService.getAllAttackJobNotExecutedQueue().getData();
        // System.out.println("*".repeat(50));
        // System.out.println(jobs);
        // System.out.println("*".repeat(50));

    }

}
