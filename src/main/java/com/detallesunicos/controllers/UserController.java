package com.detallesunicos.v1.controllers;

import com.detallesunicos.v1.model.User;
import com.detallesunicos.v1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController {

    @Autowired
        private UserRepository userRepository;

        @GetMapping("/users")
        public List<User> getAllUsers() {
            return userRepository.findAll();
        }
}