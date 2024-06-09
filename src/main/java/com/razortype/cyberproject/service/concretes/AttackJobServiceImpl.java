package com.razortype.cyberproject.service.concretes;

import com.razortype.cyberproject.api.dto.AttackJobCreateRequest;
import com.razortype.cyberproject.api.dto.AttackJobResponse;
import com.razortype.cyberproject.api.dto.UpdateAttackRequest;
import com.razortype.cyberproject.core.enums.Role;
import com.razortype.cyberproject.core.results.*;
import com.razortype.cyberproject.core.utils.AttackJobUtil;
import com.razortype.cyberproject.entity.AttackJob;
import com.razortype.cyberproject.entity.LogBlock;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.repository.AttackJobRepository;
import com.razortype.cyberproject.service.abstracts.AttackJobService;
import com.razortype.cyberproject.service.abstracts.LogService;
import com.razortype.cyberproject.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttackJobServiceImpl implements AttackJobService {

    private final AttackJobRepository attackJobRepo;
    private final UserService userService;
    private final LogService logService;

    private final AttackJobUtil attackJobUtil;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public DataResult<List<AttackJobResponse>> getAllAttackJobs() {

        List<AttackJob> attackJobs = attackJobRepo.findAll();
        List<AttackJobResponse> responses = attackJobUtil.mapAttackJobResponses(attackJobs);
        return new SuccessDataResult<>(responses, "AttackJobs fetched");

    }

    @Override
    public Result create(AttackJobCreateRequest request) {

        DataResult result = userService.getUserById(request.getAgentId());
        if (!result.isSuccess()) {
            return new ErrorResult(result.getMessage());
        }
        User agent = (User) result.getData();

        if (agent.getRole() != Role.AGENT) {
            return new ErrorResult("Attack should be assign to AGENT not " + agent.getRole().name());
        }

        LogBlock logBlock = LogBlock.builder()
                .acceptingLog(true)
                .warnCount(0)
                .errorCount(0)
                .build();

        AttackJob attackJob = AttackJob.builder()
                .attackName(request.getAttackName())
                .attackDescription(request.getAttackDescription())
                .attackType(request.getAttackType())
                .active(false)
                .crashed(false)
                .started(false)
                .executeAt(request.getExecuteAt())
                .agent(agent)
                .logBlock(logBlock)
                .build();

        logBlock.setAttackJob(attackJob);

        save(attackJob);
        logService.save(logBlock);

        return new SuccessResult("Attack job created");

    }

    @Override
    public DataResult<AttackJob> getAttackJobById(UUID id) {

        AttackJob attackJob = attackJobRepo.findById(id).orElse(null);
        if (attackJob == null) {
            return new ErrorDataResult<>("AttackJob not found: " + id);
        }
        return new SuccessDataResult<>(attackJob, "Attack Job found");

    }

    @Override
    public DataResult<AttackJobResponse> getAttackJobResponseById(UUID id) {

        DataResult<AttackJob> attackJobResult = getAttackJobById(id);
        if (!attackJobResult.isSuccess()) {
            return new ErrorDataResult<>(attackJobResult.getMessage());
        }

        AttackJobResponse response = attackJobUtil.mapAttackJobResponse(attackJobResult.getData());
        return new SuccessDataResult<>(response, attackJobResult.getMessage());

    }

    @Override
    public Result updateAttackJob(UUID id, UpdateAttackRequest request) {

        DataResult attackResult = getAttackJobById(id);
        if (!attackResult.isSuccess()) {
            return new ErrorResult(attackResult.getMessage());
        }
        AttackJob attackJob = (AttackJob) attackResult.getData();

        attackJob.setAttackName(request.getAttackName());
        attackJob.setAttackDescription(request.getAttackDescription());

        Result saveResult = save(attackJob);
        if (!saveResult.isSuccess()) {
            return saveResult;
        }

        return new SuccessResult("AttackJob updated");

    }

    @Override
    public Result deleteAttackJob(UUID id) {

        DataResult attackResult = getAttackJobById(id);
        if (!attackResult.isSuccess()) {
            return new ErrorResult(attackResult.getMessage());
        }
        AttackJob attackJob = (AttackJob) attackResult.getData();

        attackJobRepo.delete(attackJob);

        return new SuccessResult("AttackJob deleted");

    }

    @Override
    public Result save(AttackJob attackJob) {

        try {
            attackJobRepo.save(attackJob);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("AttackJob saved");

    }

    @Override
    public DataResult<List<AttackJob>> getAllAttackJobNotExecutedQueue() {

        LocalDateTime now = LocalDateTime.now();
        List<AttackJob> attackJobs = attackJobRepo.getAttackQueue(now);
        return new SuccessDataResult<>(attackJobs, "All not executed AttackJobs listed");

    }
}
