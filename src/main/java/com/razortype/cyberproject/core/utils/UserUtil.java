package com.razortype.cyberproject.core.utils;

import com.razortype.cyberproject.api.dto.AttackJobResponse;
import com.razortype.cyberproject.api.dto.UserResponse;
import com.razortype.cyberproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUtil {

    private final AttackJobUtil attackJobUtil;

    public List<UserResponse> mapToUserResponses(List<User> users) {

        return users.stream()
                .map(this::mapToUserResponse)
                .toList();

    }

    public UserResponse mapToUserResponse(User user) {

        List<AttackJobResponse> attackJobResponses = attackJobUtil.mapAttackJobResponses(user.getAttacks());

        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .role(user.getRole())
                .attacks(attackJobResponses)
                .build();
    }

}
