package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.api.dto.LogBlockResponse;
import com.razortype.cyberproject.entity.LogBlock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogBlockUtil {

    public List<LogBlockResponse> mapToBlockResponses(List<LogBlock> blocks) {

        return blocks.stream()
                .map(this::mapToBlockResponse)
                .toList();

    }

    public LogBlockResponse mapToBlockResponse(LogBlock block) {

        return LogBlockResponse.builder()
                .id(block.getId())
                .attackJobId(block.getAttackJob().getId())
                .acceptingLog(block.isAcceptingLog())
                .warnCount(block.getWarnCount())
                .errorCount(block.getErrorCount())
                .logAmount(block.getLogs().size())
                .build();

    }

}
