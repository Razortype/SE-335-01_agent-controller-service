package com.razortype.cyberproject.api.controller;

import com.razortype.cyberproject.api.dto.AgentInfoResponse;
import com.razortype.cyberproject.service.abstracts.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final AgentService agentService;

    @GetMapping("/info-response")
    List<AgentInfoResponse> getAllAgentInfoResponseTest() {
        return agentService.getConnectedAgentInfos();
    }

}
