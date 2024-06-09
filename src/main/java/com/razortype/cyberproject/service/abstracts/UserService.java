package com.razortype.cyberproject.service.abstracts;

import com.razortype.cyberproject.api.dto.UserResponse;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.entity.User;

import java.util.List;

public interface UserService {

    DataResult<User> getUserByEmail(String email);
    DataResult<User> getUserById(int id);

    DataResult<List<UserResponse>> getAllAgents();
    DataResult<UserResponse> getAgentById(int id);

}
