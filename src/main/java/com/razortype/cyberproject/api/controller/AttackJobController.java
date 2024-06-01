package com.razortype.cyberproject.api.controller;

import com.razortype.cyberproject.api.dto.AttackJobCreateRequest;
import com.razortype.cyberproject.api.dto.AttackJobResponse;
import com.razortype.cyberproject.api.dto.UpdateAttackRequest;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.entity.AttackJob;
import com.razortype.cyberproject.service.abstracts.AttackJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/attack-job")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class AttackJobController {

    private final AttackJobService attackJobService;

    @GetMapping
    public ResponseEntity<DataResult<List<AttackJobResponse>>> getAllAttackJobs() {

        DataResult<List<AttackJobResponse>> result = attackJobService.getAllAttackJobs();
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PostMapping
    @Operation(summary = "Create Attack Job for Schedule", description = "Create attack job for schedule to executed when \"execute_at\" date comes")
    public ResponseEntity<Result> createScheduledAttack(@RequestBody AttackJobCreateRequest request) {

        Result result = attackJobService.create(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @GetMapping("/{attack-id}")
    @Operation(summary = "Get Attack by Id", description = "Getting attack job content by its id parameter")
    public ResponseEntity<DataResult<AttackJobResponse>> getAttackById(@PathVariable("attack-id") UUID id) {

        DataResult<AttackJobResponse> result = attackJobService.getAttackJobResponseById(id);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PutMapping("/{attack-id}")
    @Operation(summary = "Update Attack by Id", description = "Updating attack job content by its id parameter")
    public ResponseEntity<Result> updateAttackById(@PathVariable("attack-id") UUID id, @RequestBody UpdateAttackRequest request) {

        Result result = attackJobService.updateAttackJob(id, request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @DeleteMapping("/{attack-id}")
    @Operation(summary = "Delete Attack By Id", description = "Deleting attack job by its id parameter")
    public ResponseEntity<Result> deleteAttackById(@PathVariable("attack-id") UUID id) {

        Result result = attackJobService.deleteAttackJob(id);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

}
