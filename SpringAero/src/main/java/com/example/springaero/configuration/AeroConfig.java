package com.example.springaero.configuration;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AeroConfig {
    @Bean
    public AerospikeClient client() {
        AerospikeClient client = null;
        while (true) {
            try {
                client = new AerospikeClient("aerospike", 3000);
                break;
            } catch (AerospikeException e) {
                continue;
            }
        }
        return client;


    }

}
