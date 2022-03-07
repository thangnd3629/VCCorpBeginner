package com.example.springaero.model;

import lombok.Data;

@Data
public class Session {
    private String sessionId;
    private String userId;
    private String expDate;
}
