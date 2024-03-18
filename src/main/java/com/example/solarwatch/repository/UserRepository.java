package com.example.solarwatch.repository;

import com.example.solarwatch.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.lang.String.format;

/*
@Repository
public class UserRepository {

    private final ConcurrentMap<String, UserEntity> users = new ConcurrentHashMap<>();

    public synchronized Optional<UserEntity> findUserByName(String userName) {
        return Optional.ofNullable(users.get(userName));
    }

    public synchronized void createUser(UserEntity user) {
        String userName = user.username();
        if (users.containsKey(userName)) {
            throw new IllegalArgumentException(format("user %s already exists", userName));
        }
        users.put(userName, user);
    }

    public synchronized void updateUser(UserEntity user) {
        String username = user.username();
        if (!users.containsKey(username)) {
            throw new IllegalArgumentException(format("User %s does not exist", username));
        }
        users.put(username, user);
    }
}*/

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    default UserEntity createUser(UserEntity user) {
        String username = user.getUsername();
        if (findByUsername(username).isPresent()) {
            throw new IllegalArgumentException(String.format("User %s already exists", username));
        }
        return save(user);
    }

    default UserEntity updateUser(UserEntity user) {
        String username = user.getUsername();
        if (!findByUsername(username).isPresent()) {
            throw new IllegalArgumentException(String.format("User %s does not exist", username));
        }
        return save(user);
    }
}