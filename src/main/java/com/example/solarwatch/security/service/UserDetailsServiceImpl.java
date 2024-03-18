package com.example.solarwatch.security.service;

import com.example.solarwatch.model.user.UserEntity;
import com.example.solarwatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

/*    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (Role role : userEntity.roles()) {
            roles.add(new SimpleGrantedAuthority(role.name()));
        }

        return new org.springframework.security.core.userdetails.User(userEntity.username(), userEntity.password(), roles);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole().name());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(role));
    }
}
