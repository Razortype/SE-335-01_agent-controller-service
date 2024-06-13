package com.razortype.cyberproject.service.abstracts;

import com.razortype.cyberproject.api.dto.AttackJobCreateRequest;
import com.razortype.cyberproject.api.dto.AttackJobResponse;
import com.razortype.cyberproject.api.dto.DashboardAttackResponse;
import com.razortype.cyberproject.api.dto.UpdateAttackRequest;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.entity.AttackJob;
import com.razortype.cyberproject.entity.User;

import java.util.List;
import java.util.UUID;

public interface AttackJobService {

    DataResult<List<AttackJobResponse>> getAllAttackJobs();
    Result create(AttackJobCreateRequest request);
    DataResult<AttackJob> getAttackJobById(UUID id);
    DataResult<AttackJobResponse> getAttackJobResponseById(UUID id);
    Result updateAttackJob(UUID id, UpdateAttackRequest request);
    Result deleteAttackJob(UUID id);
    Result save(AttackJob attackJob);

    DataResult<List<AttackJob>> getAllAttackJobNotExecutedQueue();
    DataResult<DashboardAttackResponse> getDashboardAttacks();

}
