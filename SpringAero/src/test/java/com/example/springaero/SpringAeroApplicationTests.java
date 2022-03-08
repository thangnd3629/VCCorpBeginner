package com.example.springaero;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.policy.WritePolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
class SpringAeroApplicationTests {

    @Autowired
    private AerospikeClient client;
    @Test
    void test(){
        String sessionId = UUID.randomUUID().toString();
        WritePolicy policy = new WritePolicy();
        policy.setTimeout(500);
        Key key = new Key("session-storage", "session", sessionId);
        Bin binExp = new Bin("exp", new Date(new Date().getTime() + (1000 * 60 * 60 * 24)));
        Bin binUser = new Bin("uid", 1);
        client.put(policy, key, binUser, binExp);
    }

}
