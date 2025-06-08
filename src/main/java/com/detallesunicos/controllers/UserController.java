package com.detallesunicos.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    @GetMapping("/user")
    public String getAuthenticatedUser() {
        return "User correct";
    }
}