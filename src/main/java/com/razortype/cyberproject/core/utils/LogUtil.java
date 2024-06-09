package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.api.dto.LogResponse;
import com.razortype.cyberproject.entity.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogUtil {

    public List<LogResponse> mapToLogResponses(List<Log> logs) {

        return logs.stream()
                .map(this::mapToLogResponse)
                .toList();

    }

    public LogResponse mapToLogResponse(Log log) {

        return LogResponse.builder()
                .id(log.getId())
                .logText(log.getLogText())
                .logType(log.getLogType())
                .logLevel(log.getLogLevel())
                .createdAt(log.getCreatedAt())
                .build();

    }

}
