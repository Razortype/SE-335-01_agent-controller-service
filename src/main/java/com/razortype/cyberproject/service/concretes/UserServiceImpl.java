package com.razortype.cyberproject.service.concretes;

import com.razortype.cyberproject.api.dto.UserResponse;
import com.razortype.cyberproject.core.enums.Role;
import com.razortype.cyberproject.core.utils.UserUtil;
import com.razortype.cyberproject.repository.UserRepository;
import com.razortype.cyberproject.core.results.DataResult;
import com.razortype.cyberproject.core.results.ErrorDataResult;
import com.razortype.cyberproject.core.results.SuccessDataResult;
import com.razortype.cyberproject.entity.User;
import com.razortype.cyberproject.service.abstracts.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final UserUtil userUtil;


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

    @Override
    public DataResult<List<UserResponse>> getAllAgents() {

        List<User> agents = userRepo.getAllByRole(Role.AGENT);
        List<UserResponse> responses = userUtil.mapToUserResponses(agents);
        return new SuccessDataResult<>(responses, "All Agents fetched");

    }

    @Override
    public DataResult<UserResponse> getAgentById(int id) {

        User agent = userRepo.findUserByIdAndRole(id, Role.AGENT).orElse(null);
        if (agent == null) {
            return new ErrorDataResult<>("Agent not found: " + id);
        }
        UserResponse response = userUtil.mapToUserResponse(agent);
        return new SuccessDataResult<>(response, "Agent found");

    }

}
