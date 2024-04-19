package com.razortype.cyberproject.api.controller;

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
    @Operation(summary = "Test", description = "Test")
    public ResponseEntity<DataResult<AttackJob>> getAttackById(@PathVariable("attack-id") UUID id) {return null;}

    @PutMapping("{attack-id}")
    @Operation(summary = "Test", description = "Test")
    public ResponseEntity<DataResult<AttackJob>> updateAttackById(@PathVariable("attack-id") UUID id) {return null;}

    @DeleteMapping("{attack-id}")
    @Operation(summary = "Test", description = "Test")
    public ResponseEntity<DataResult<AttackJob>> deleteAttackById(@PathVariable("attack-id") UUID id) {return null;}

}
