package com.example.solarwatch.controller;


import com.example.solarwatch.dto.user.CreateUserRequest;
import com.example.solarwatch.dto.user.JwtResponse;
import com.example.solarwatch.dto.user.UserRequest;
import com.example.solarwatch.model.user.Role;
import com.example.solarwatch.model.user.UserEntity;
import com.example.solarwatch.repository.UserRepository;
import com.example.solarwatch.security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserController(
            UserRepository userRepository,
            PasswordEncoder encoder,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest signUpRequest) {

        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        UserEntity user = new UserEntity();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Void> registerAdmin(@RequestBody CreateUserRequest signUpRequest) {
        UserEntity adminUser = new UserEntity();
        adminUser.setUsername(signUpRequest.getUsername());
        adminUser.setEmail(signUpRequest.getEmail());
        adminUser.setPassword(encoder.encode(signUpRequest.getPassword()));
        adminUser.setRole(Role.ROLE_ADMIN);

        userRepository.createUser(adminUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse(null);

        if (Role.ROLE_ADMIN.name().equals(role)) {
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), Role.ROLE_ADMIN));
        } else if (Role.ROLE_USER.name().equals(role)) {
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), Role.ROLE_USER));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid role");
        }
    }
}
