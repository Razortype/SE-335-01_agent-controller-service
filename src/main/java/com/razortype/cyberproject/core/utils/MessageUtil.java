package com.razortype.cyberproject.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.razortype.cyberproject.api.dto.AgentInfoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(List<AgentInfoResponse> agentInfoResponses) throws JsonProcessingException {
        return objectMapper.writeValueAsString(agentInfoResponses);
    }

}
