package com.razortype.cyberproject.api.controller;

import com.razortype.cyberproject.api.dto.LogBlockResponse;
import com.razortype.cyberproject.api.dto.LogResponse;
import com.razortype.cyberproject.api.dto.NewLogRequest;
import com.razortype.cyberproject.api.dto.UpdateLogBlockRequest;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.entity.Log;
import com.razortype.cyberproject.entity.LogBlock;
import com.razortype.cyberproject.service.abstracts.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/log")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class LogController {

    private final LogService logService;

    Logger logger = LoggerFactory.getLogger(LogController.class);

    // Log Block Controller

    @GetMapping("/block")
    @Operation(summary = "Get All Log Blocks", description = "Retrieve a list of all log blocks.")
    public ResponseEntity<DataResult<List<LogBlockResponse>>> getAllLogBlocks() {
        logger.info("Request received to retrieve all log blocks");
        DataResult<List<LogBlockResponse>> result = logService.getAllLogBlockResponse();

        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);
    }

    @GetMapping("/block/{block-id}")
    @Operation(summary = "Get Log Block by ID", description = "Retrieve a log block by its unique ID.")
    public ResponseEntity<DataResult<LogBlockResponse>> getLogBlockById(@PathVariable(name = "block-id") UUID id) {
        logger.info("Request received to retrieve log block by ID: {}", id);
        DataResult<LogBlockResponse> result = logService.getLogBlockResponseById(id);

        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);
    }

    @PostMapping("/block/{block-id}")
    @Operation(summary = "Update Log Block by ID", description = "Update a log block with the provided ID.")
    public ResponseEntity<Result> updateLogBlockById(@PathVariable(name = "block-id") UUID id,
                                                     @RequestBody UpdateLogBlockRequest request) {
        logger.info("Request received to update log block with ID: {}", id);
        Result result = logService.updateLogBlock(id, request);

        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);
    }

    @PostMapping("/block/{block-id}/log")
    @Operation(summary = "Save New Log to Log Block", description = "Save a new log entry to the specified log block.")
    public ResponseEntity<Result> saveNewLogToLogBlock(@PathVariable(name = "block-id") UUID id,
                                                       @RequestBody NewLogRequest request) {
        logger.info("Request received to save new log to log block with ID: {}", id);
        Result result = logService.saveNewLogToBlock(id, request);

        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);
    }

    @GetMapping("/block/{block-id}/log")
    @Operation(summary = "Get logs by log block", description = "Retrieve logs belonging to a specific log block with pagination.")
    public ResponseEntity<DataResult<List<LogResponse>>> getLogByLogBlock(
            @PathVariable(name = "block-id") UUID logBlockId,
            @RequestParam int page,
            @RequestParam int size) {
        logger.info("Request received to retrieve logs for log block with ID: {}", logBlockId);

        DataResult<List<LogResponse>> logsResult = logService.getLogResponseByLogBlock(logBlockId, page, size);

        logsResult.determineHttpStatus();
        return ResponseEntity.status(logsResult.getHttpStatus())
                .body(logsResult);
    }

    @GetMapping("/block/{block-id}/log/page-amount")
    public ResponseEntity<DataResult<Long>> getLogByLogBlock(
            @PathVariable(name = "block-id") UUID logBlockId,
            @RequestParam int size) {

        DataResult<Long> result = logService.getLogPageAmountByLobBlockId(logBlockId, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    // Log Controller

    @GetMapping
    @Operation(summary = "Get All Logs", description = "Retrieve a list of all logs.")
    public ResponseEntity<DataResult<List<LogResponse>>> getAllLog() {
        logger.info("Request received to retrieve all logs");
        DataResult<List<LogResponse>> result = logService.getAllLogResponse();

        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);
    }

    @GetMapping("/{log-id}")
    @Operation(summary = "Get Log by ID", description = "Retrieve a log by its unique ID.")
    public ResponseEntity<DataResult<LogResponse>> getLogById(@PathVariable(name = "log-id") UUID id) {
        logger.info("Request received to retrieve log by ID: {}", id);
        DataResult<LogResponse> result = logService.getLogResponseById(id);

        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);
    }


}
