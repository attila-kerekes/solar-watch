package com.example.solarwatch.dto.user;

import com.example.solarwatch.model.user.Role;

import java.util.List;

public record JwtResponse (String jwt, String userName, Role role) {
}
