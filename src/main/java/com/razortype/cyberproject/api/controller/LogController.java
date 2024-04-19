package com.razortype.cyberproject.api.controller;

import com.razortype.cyberproject.api.dto.NewLogRequest;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.entity.Log;
import com.razortype.cyberproject.entity.LogBlock;
import com.razortype.cyberproject.service.abstracts.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
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

    // Log Block Controller

    @GetMapping("/block")
    @Operation(summary = "Test", description = "Test")
    public ResponseEntity<DataResult<List<LogBlock>>> getAllLogBlocks() {return null;}

    @GetMapping("/block/{block-id}")
    @Operation(summary = "Test", description = "Test")
    public ResponseEntity<DataResult<LogBlock>> getLogBlockById(@PathVariable(name = "block-id") UUID id) {return null;}

    @PostMapping("/block/{block-id}")
    @Operation(summary = "Test", description = "Test")
    public ResponseEntity<Result> saveNewLogToLogBlock(@PathVariable(name = "block-id") UUID id, @RequestBody NewLogRequest request) {return null;}

    // Log Controller

    @GetMapping
    @Operation(summary = "Test", description = "Test")
    public ResponseEntity<DataResult<List<Log>>> getAllLog() {return null;}

    @GetMapping("/{log-id}")
    @Operation(summary = "Test", description = "Test")
    public ResponseEntity<DataResult<Log>> getLogById(@PathVariable(name = "log-id") UUID id) {return null;}


}
