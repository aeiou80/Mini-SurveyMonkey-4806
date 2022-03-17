package com.example.demo.controller;

import com.example.demo.Views;
import com.example.demo.model.User;
import com.example.demo.repository.UsersRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UsersRepository usersRepository;

    @JsonView(Views.Public.class)
    @GetMapping("/user")
    public List<User> get() {
        return usersRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User get(@PathVariable long id) {
        return usersRepository.findById(id).get();
    }

    /**
     * Create a user and encode their password
     */
    @JsonView(Views.Public.class)
    @PostMapping("/user")
    public User create(@RequestBody User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return usersRepository.save(user);
    }
}
