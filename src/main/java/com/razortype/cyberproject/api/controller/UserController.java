package com.razortype.cyberproject.api.controller;

import com.razortype.cyberproject.api.dto.UserResponse;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.service.abstracts.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    private final UserService userService;

    @GetMapping("/agent")
    public ResponseEntity<DataResult<List<UserResponse>>> getAllAgents() {

        DataResult<List<UserResponse>> result = userService.getAllAgents();
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @GetMapping("/agent/{agent-id}")
    public ResponseEntity<DataResult<UserResponse>> getAgentById(@PathVariable("agent-id") int agentId) {

        DataResult<UserResponse> result = userService.getAgentById(agentId);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

}
