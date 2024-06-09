package com.razortype.cyberproject.service.abstracts;

import com.razortype.cyberproject.api.dto.LogBlockResponse;
import com.razortype.cyberproject.api.dto.LogResponse;
import com.razortype.cyberproject.api.dto.NewLogRequest;
import com.razortype.cyberproject.api.dto.UpdateLogBlockRequest;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.Result;
import com.razortype.cyberproject.entity.AttackJob;
import com.razortype.cyberproject.entity.Log;
import com.razortype.cyberproject.entity.LogBlock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface LogService {

    Result save(LogBlock logBlock);
    Result save(Log log);
    DataResult<List<LogBlock>> getAllLogBlock();
    DataResult<List<LogBlockResponse>> getAllLogBlockResponse();
    DataResult<LogBlock> getLogBlockById(UUID id);
    DataResult<LogBlockResponse> getLogBlockResponseById(UUID id);
    DataResult<LogBlock> getLogBlockByAttackJob(AttackJob attackJob);
    Result updateLogBlock(UUID id, UpdateLogBlockRequest request);
    Result saveNewLogToBlock(UUID logBlockId, NewLogRequest request);
    DataResult<Page<Log>> getLogByLogBlock(UUID logBlockId, int page, int size);
    DataResult<List<LogResponse>> getLogResponseByLogBlock(UUID logBlockId, int page, int size);
    Result delete(LogBlock logBlock);

    DataResult<List<Log>> getAllLog();
    DataResult<List<LogResponse>> getAllLogResponse();
    DataResult<Log> getLogById(UUID id);
    DataResult<LogResponse> getLogResponseById(UUID id);
    Result delete(Log log);



}
