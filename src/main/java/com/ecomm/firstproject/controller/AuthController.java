package com.ecomm.firstproject.controller;

import com.ecomm.firstproject.model.User;
import com.ecomm.firstproject.repository.UserRepository;
import com.ecomm.firstproject.security.JwtUtil;
import exceptions.BadUserCredentialException;
import exceptions.EmailAlreadyExists;
import exceptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        if (userRepository.findByEmailId(user.getEmailId()).isPresent()) {
            throw new EmailAlreadyExists();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmailId(), user.getRole().name());
        return Map.of("token", token);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
        User dbUser = userRepository.findByEmailId(user.getEmailId())
                .orElseThrow(() -> new UserNotFound());

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new BadUserCredentialException();
        }

        String token = jwtUtil.generateToken(dbUser.getEmailId(), dbUser.getRole().name());
        return Map.of("token", token);
    }
}
