package com.ecomm.firstproject.service;

import com.ecomm.firstproject.model.Role;
import com.ecomm.firstproject.model.User;
import com.ecomm.firstproject.repository.UserRepository;
import exceptions.EmailAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(String username, String password, Role role) {
        if (userRepository.findByEmailId(username).isPresent()) {
            throw new EmailAlreadyExists("Username already exists");
        }
        User user = User.builder()
                .emailId(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByEmailId(username);
    }
}
