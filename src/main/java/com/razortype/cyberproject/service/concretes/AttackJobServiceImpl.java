package com.razortype.cyberproject.service.concretes;

import com.razortype.cyberproject.Repository.AttackJobRepository;
import com.razortype.cyberproject.service.abstracts.AttackJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttackJobServiceImpl implements AttackJobService {

    private final AttackJobRepository attackJobRepo;

}
