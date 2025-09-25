package com.auth.aService.Service;

import com.auth.aService.Model.User;
import com.auth.aService.Repository.UserRepository;
import com.auth.aService.Security.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String login(String email, String rawPassword) {
        Optional<User> userEmail = userRepository.findByEmail(email);
        if (userEmail.isEmpty()) {
            throw new UsernameNotFoundException("Enter correct email");
        }
        User user = userEmail.get();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return jwtUtil.generateToken(String.valueOf(user.getId()));

    }


}
