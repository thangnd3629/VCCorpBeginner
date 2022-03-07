package com.example.springaero.controller;


import com.example.springaero.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password){
        String sessionId = userService.login(username, password);
        return sessionId;
    }
    @GetMapping("/logout")
    public String logout(@RequestParam String sessionId){
        userService.logout(sessionId);
        return "Logged out";
    }
    @GetMapping("/do-sth")
    public String dosth(@RequestParam String sessionId){

        return userService.handleRequest(sessionId);
    }
}
