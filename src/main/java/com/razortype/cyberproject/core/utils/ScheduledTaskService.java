package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.entity.AttackJob;
import com.razortype.cyberproject.service.abstracts.AttackJobService;
import com.razortype.cyberproject.service.abstracts.SocketSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTaskService {

    private final AttackJobService attackJobService;
    private final SocketSessionService socketSessionService;

    @Scheduled(fixedRate = 5000)
    public void executeJobTask() {

        List<AttackJob> jobs = attackJobService.getAllAttackJobNotExecutedQueue().getData();

        jobs.stream()
                .map(socketSessionService::sendAttackToClient)
                .filter(result -> !result.isSuccess())
                .forEach(System.out::println);

    }

}
