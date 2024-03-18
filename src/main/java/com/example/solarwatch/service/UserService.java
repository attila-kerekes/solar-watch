package com.example.solarwatch.service;

import com.example.solarwatch.model.user.Role;
import com.example.solarwatch.model.user.UserEntity;
import com.example.solarwatch.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findCurrentUser() {
        UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        String username = contextUser.getUsername();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(format("could not find user %s in the repository", username)));
    }

/*    public void addRoleFor(User user, Role role) {

        Set<Role> oldRoles = user.roles();

        Set<Role> copiedRoles = new HashSet<>(oldRoles);
        copiedRoles.add(role);

        userRepository.updateUser(new User(user.username(), user.password(), Set.copyOf(copiedRoles)));
    }*/

    public void addRoleFor(UserEntity user, Role role) {

        user.setRole(role);
        userRepository.updateUser(user);
    }
}
