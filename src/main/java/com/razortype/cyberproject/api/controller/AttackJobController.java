package com.razortype.cyberproject.api.controller;

import com.razortype.cyberproject.api.dto.UpdateAttackRequest;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.entity.AttackJob;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/attack-job")
@SecurityRequirement(name = "Bearer Authentication")
public class AttackJobController {

    @GetMapping
    public ResponseEntity<DataResult<List<AttackJob>>> getAllAttackJobs() {return null;}

    @GetMapping("{attack-id}")
    @Operation(summary = "Get Attack by Id", description = "Getting attack job content by its id parameter")
    public ResponseEntity<DataResult<AttackJob>> getAttackById(@PathVariable("attack-id") UUID id) {return null;}

    @PutMapping("{attack-id}")
    @Operation(summary = "Update Attack by Id", description = "Updating attack job content by its id parameter")
    public ResponseEntity<DataResult<AttackJob>> updateAttackById(@PathVariable("attack-id") UUID id, @RequestBody UpdateAttackRequest request) {return null;}

    @DeleteMapping("{attack-id}")
    @Operation(summary = "Delete Attack By Id", description = "Deleting attack job by its id parameter")
    public ResponseEntity<DataResult<AttackJob>> deleteAttackById(@PathVariable("attack-id") UUID id) {return null;}

}
