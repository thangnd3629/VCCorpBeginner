package com.example.springaero.service;

import com.example.springaero.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AerospikeService {
    @Autowired
    private SessionRepository sessionRepository;
    public String createSession(String userId){
        return sessionRepository.createSession(userId);

    }

}
