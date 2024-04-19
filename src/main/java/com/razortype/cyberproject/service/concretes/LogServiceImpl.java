package com.razortype.cyberproject.service.concretes;

import com.razortype.cyberproject.Repository.LogBlockRepository;
import com.razortype.cyberproject.Repository.LogRepository;
import com.razortype.cyberproject.service.abstracts.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogBlockRepository logBlockRepo;
    private final LogRepository logRepo;

}
