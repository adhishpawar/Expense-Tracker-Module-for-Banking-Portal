package com.adhish.Expense.Tracker.service;

import com.adhish.Expense.Tracker.entity.User;
import com.adhish.Expense.Tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); //DEfault Role
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
}
