package com.razortype.cyberproject.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardAttackResponse {

    @JsonProperty("today")
    private List<AttackJobResponse> todayAttacks;

    @JsonProperty("yesterday")
    private List<AttackJobResponse> yesterdayAttacks;
}
