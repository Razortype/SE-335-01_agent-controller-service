package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.razortype.cyberproject.core.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @JsonProperty("user_id")
    private int id;

    private String firstname;
    private String lastname;
    private String email;

    @JsonProperty("created_at")
    private Date createdAt;

    private Role role;

    private List<AttackJobResponse> attacks;

}
