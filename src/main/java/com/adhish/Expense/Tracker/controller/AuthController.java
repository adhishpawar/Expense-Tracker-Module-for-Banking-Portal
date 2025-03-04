package com.adhish.Expense.Tracker.controller;

import com.adhish.Expense.Tracker.config.JwtUtil;
import com.adhish.Expense.Tracker.entity.User;
import com.adhish.Expense.Tracker.service.CustomUserDetailsService;
import com.adhish.Expense.Tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok().body(Map.of("token", token));
    }
}
