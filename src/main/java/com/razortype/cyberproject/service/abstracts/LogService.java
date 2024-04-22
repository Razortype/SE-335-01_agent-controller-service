package com.razortype.cyberproject.service.abstracts;

import com.razortype.cyberproject.api.dto.NewLogRequest;
import com.razortype.cyberproject.api.dto.UpdateLogBlockRequest;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.Result;
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
    DataResult<LogBlock> getLogBlockById(UUID id);
    Result updateLogBlock(UUID id, UpdateLogBlockRequest request);
    Result saveNewLogToBlock(UUID logBlockId, NewLogRequest request);
    DataResult<Page<Log>> getLogByLogBlock(UUID logBlockId, Pageable pageable);

    DataResult<List<Log>> getAllLog();
    DataResult<Log> getLogById(UUID id);

}
