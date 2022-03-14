package com.example.springaero.service;

import com.example.springaero.model.Session;
import com.example.springaero.model.User;
import com.example.springaero.repository.SessionRepository;
import com.example.springaero.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public String login(String username, String password){
        String sessionId=null;

        Optional<User> user =  userRepository.findUserByUsername(username);
        if (user.isPresent()){
            if (isValidUser(user.get(), password)){
                String uid = user.get().getId();
                sessionId = sessionRepository.createSession(uid);
            }
        }
        return sessionId;

    }
    public void logout(String sessionId){
        sessionRepository.invalidateSession(sessionId);
    }
    private boolean isValidUser(User user, String credential){
        return user.getPassword().equals(credential);
    }
    public String handleRequest(String sessionId){
        Session session = sessionRepository.findSession(sessionId);
        if (session == null){
            return "Login to send request";
        }
        return "Cache hit --- authorized user "+session.getUserId();
    }
}
