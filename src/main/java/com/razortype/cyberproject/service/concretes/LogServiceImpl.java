package com.razortype.cyberproject.service.concretes;

import com.razortype.cyberproject.Repository.LogBlockRepository;
import com.razortype.cyberproject.Repository.LogRepository;
import com.razortype.cyberproject.api.controller.LogController;
import com.razortype.cyberproject.api.dto.NewLogRequest;
import com.razortype.cyberproject.api.dto.UpdateLogBlockRequest;
import com.razortype.cyberproject.core.enums.Role;
import com.razortype.cyberproject.core.results.*;
import com.razortype.cyberproject.core.utils.AuthUserUtil;
import com.razortype.cyberproject.entity.Log;
import com.razortype.cyberproject.entity.LogBlock;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.LogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogBlockRepository logBlockRepo;
    private final LogRepository logRepo;

    private final AuthUserUtil authUserUtil;

    Logger logger = LoggerFactory.getLogger(LogController.class);

    @Override
    public Result save(LogBlock logBlock) {
        try {
            logBlockRepo.save(logBlock);
            logger.info("LogBlock saved: {}", logBlock.getId());
        } catch (Exception e) {
            String errorMessage = "Unexpected Error Occurred while saving LogBlock: " + e.getMessage();
            logger.error(errorMessage, e);
            return new ErrorResult(errorMessage);
        }
        return new SuccessResult("LogBlock saved");
    }

    @Override
    public Result save(Log log) {
        try {
            logRepo.save(log);
            logger.info("Log saved: {}", log.getId());
        } catch (Exception e) {
            String errorMessage = "Unexpected Error Occurred while saving Log: " + e.getMessage();
            logger.error(errorMessage, e);
            return new ErrorResult(errorMessage);
        }
        return new SuccessResult("Log saved");
    }

    @Override
    public DataResult<List<LogBlock>> getAllLogBlock() {
        logger.debug("Attempting to fetch all LogBlocks");
        List<LogBlock> logBlocks = logBlockRepo.findAll();
        logger.debug("Fetched {} LogBlocks", logBlocks.size());
        logger.info("All LogBlocks fetched");
        return new SuccessDataResult<>(logBlocks, "LogBlock fetched");
    }

    @Override
    public DataResult<LogBlock> getLogBlockById(UUID id) {
        logger.debug("Attempting to retrieve LogBlock with ID: {}", id);
        LogBlock logBlock = logBlockRepo.findById(id).orElse(null);
        if (logBlock == null) {
            String errorMessage = "LogBlock not found with ID: " + id;
            logger.warn(errorMessage);
            return new ErrorDataResult<>(errorMessage);
        }
        logger.info("LogBlock found with ID: {}", id);
        return new SuccessDataResult<>(logBlock, "LogBlock found");
    }

    @Override
    public Result updateLogBlock(UUID id, UpdateLogBlockRequest request) {
        logger.debug("Attempting to update LogBlock with ID: {} and request: {}", id, request);

        DataResult result = getLogBlockById(id);
        if (!result.isSuccess()) {
            logger.warn("LogBlock not found with ID: {}", id);
            return new ErrorResult(result.getMessage());
        }
        LogBlock logBlock = (LogBlock) result.getData();

        logBlock.setAcceptingLog(request.isAcceptingLog());

        Result saveResult = save(logBlock);
        if (!saveResult.isSuccess()) {
            return saveResult;
        }
        logger.info("LogBlock updated with ID: {}", id);
        return new SuccessResult("LogBlock updated");

    }

    @Override
    public Result saveNewLogToBlock(UUID logBlockId, NewLogRequest request) {
        logger.debug("Attempting to save new Log to LogBlock with ID: {}", logBlockId);
        DataResult logBlockResult = getLogBlockById(logBlockId);
        if (!logBlockResult.isSuccess()) {
            logger.warn("LogBlock not found with ID: {}", logBlockId);
            return new ErrorResult(logBlockResult.getMessage());
        }

        User user = authUserUtil.getAuthenticatedUser();
        if (user == null) {
            logger.warn("User not Authenticated to push log");
            return new ErrorResult("User not found");
        }

        if (!user.getRole().equals(Role.AGENT)) {
            logger.warn("User role is not AGENT to push log");
            return new ErrorResult("User should be AGENT provided: " + user.getRole());
        }

        LogBlock logBlock = (LogBlock) logBlockResult.getData();

        if (!logBlock.isAcceptingLog()) {
            logger.warn("LogBlock not accepting new log: {}", logBlockId);
            return new ErrorResult("LogBlock not accepting log");
        }

        if (!logBlock.getAttackJob().getAgent().equals(user)) {
            logger.warn("LogBlock not matching with provided Agent: {} - {}", logBlockId, user.getEmail());
            return new ErrorResult("LogBlock not assigned to provided user: " + logBlockId + " - " + user.getEmail());
        }

        Log log = Log.builder()
                .logText(request.getLogText())
                .logType(request.getLogType())
                .logLevel(request.getLogLevel())
                .build();

        Result saveResult = save(log);
        if (!saveResult.isSuccess()) {
            return new ErrorResult(saveResult.getMessage());
        }

        return new SuccessResult("logged to: " + logBlockId);
    }

    @Override
    public DataResult<Page<Log>> getLogByLogBlock(UUID logBlockId, Pageable pageable) {
        logger.debug("Attempting to retrieve Logs by LogBlock with ID: {}", logBlockId);
        DataResult result = getLogBlockById(logBlockId);
        if (!result.isSuccess()) {
            return new ErrorDataResult<>(result.getMessage());
        }
        LogBlock logBlock = (LogBlock) result.getData();

        Page<Log> logs = logRepo.findByLogBlock(logBlock, pageable);
        logger.debug("Retrieved {} Logs for LogBlock with ID: {}", logs.getTotalElements(), logBlockId);
        logger.info("Logs paged: " + pageable);
        return new SuccessDataResult<>(logs, "Logs paged: " + pageable);

    }

    @Override
    public DataResult<List<Log>> getAllLog() {
        logger.debug("Attempting to fetch all Logs");
        List<Log> logs = logRepo.findAll();
        logger.debug("Fetched {} Logs", logs.size());
        return new SuccessDataResult<>(logs, "Logs fetched");
    }

    @Override
    public DataResult<Log> getLogById(UUID id) {
        logger.debug("Attempting to retrieve Log with ID: {}", id);
        Log log = logRepo.findById(id).orElse(null);
        if (log == null) {
            return new ErrorDataResult<>("Log not found: " + id);
        }
        logger.debug("Log found with ID: {}", id);
        logger.info("Log found with ID: {}", id);
        return new SuccessDataResult<>("Log found");
    }
}
