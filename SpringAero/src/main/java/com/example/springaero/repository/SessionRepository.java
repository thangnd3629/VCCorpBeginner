package com.example.springaero.repository;

import com.example.springaero.model.Session;

public interface SessionRepository {
    String createSession(String userId);
    Session findSession(String sessionId);
    String invalidateSession(String sessionId);
}
