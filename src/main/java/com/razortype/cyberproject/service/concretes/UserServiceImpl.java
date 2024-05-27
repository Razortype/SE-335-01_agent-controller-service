package com.razortype.cyberproject.service.concretes;

import com.razortype.cyberproject.repository.UserRepository;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.ErrorDataResult;
import com.razortype.cyberproject.core.results.SuccessDataResult;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;


    @Override
    public DataResult<User> getUserByEmail(String email) {
        User user = userRepo.getUserByEmail(email).orElse(null);

        if (user == null) {
            return new ErrorDataResult<>("User not found: " + email);
        }

        return new SuccessDataResult<>(user, "User found" + email);

    }

    @Override
    public DataResult<User> getUserById(int id) {

        User user = userRepo.getUserById(id).orElse(null);

        if (user == null) {
            return new ErrorDataResult<>("User not found: " + id);
        }

        return new SuccessDataResult<>(user, "User found");

    }

}
