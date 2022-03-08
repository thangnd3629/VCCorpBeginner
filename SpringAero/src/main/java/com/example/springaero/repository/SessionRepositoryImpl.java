package com.example.springaero.repository;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.example.springaero.model.Session;
import org.luaj.vm2.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionRepositoryImpl implements SessionRepository {
    @Autowired
    private AerospikeClient client;

    @Override
    public String createSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        WritePolicy policy = new WritePolicy();
        policy.setTimeout(500);
        Key key = new Key("session-storage", "session", sessionId);
        Bin binExp = new Bin("exp", new Date(new Date().getTime() + (1000 * 60 * 60 * 24)).toString());
        Bin binUser = new Bin("uid", userId);
        client.put(policy, key, binUser, binExp);
        return sessionId;
    }

    @Override
    public Session findSession(String sessionId) {
        Record record = client.get(null, new Key("session-storage", "session", sessionId));
        if (record == null)
        {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for(String binKey : record.bins.keySet())
        {
            result.put(binKey, (String) record.bins.get(binKey));
        }
        Session session = new Session();
        session.setSessionId(sessionId);
        session.setExpDate(result.get("exp"));
        session.setUserId(result.get("uid"));
        return session;
    }

    @Override
    public String invalidateSession(String sessionId) {
        return null;
    }
}
