package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.api.dto.AttackExecutionPackage;
import com.razortype.cyberproject.api.dto.AttackExecutionPackageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExecutionPackageUtil {

    List<AttackExecutionPackageResponse> mapToExecutionPackageResponses(List<AttackExecutionPackage> packages) {

        List<AttackExecutionPackageResponse> responses = packages.stream()
                .map(this::mapToExecutionPackageResponse)
                .collect(Collectors.toList());

        return responses;
    }

    AttackExecutionPackageResponse mapToExecutionPackageResponse(AttackExecutionPackage executionPackage) {

        AttackExecutionPackageResponse response = AttackExecutionPackageResponse.builder()
                .attackName(executionPackage.getAttackName())
                .attackType(executionPackage.getAttackType())
                .executedAt(executionPackage.getExecutedAt())
                .attackLogId(executionPackage.getAttackLogId())
                .build();

        return response;

    }

}
