package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.api.dto.AttackJobResponse;
import com.razortype.cyberproject.entity.AttackJob;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttackJobUtil {

    public List<AttackJobResponse> mapAttackJobResponses(List<AttackJob> jobs) {

        return jobs.stream()
                .map(this::mapAttackJobResponse)
                .toList();

    }

    public AttackJobResponse mapAttackJobResponse(AttackJob job) {
        return AttackJobResponse.builder()
                .id(job.getId())
                .attackName(job.getAttackName())
                .attackDescription(job.getAttackDescription())
                .attackType(job.getAttackType())
                .active(job.isActive())
                .crashed(job.isCrashed())
                .started(job.isStarted())
                .executeAt(job.getExecuteAt())
                .createdAt(job.getCreatedAt())
                .startExecutingAt(job.getStartExecutingAt())
                .agentId(job.getAgent().getId())
                .logBlockId(job.getLogBlock().getId())
                .build();
    }

}
