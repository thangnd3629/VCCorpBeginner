package com.example.springaero.configuration;

import com.aerospike.client.AerospikeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AeroConfig {
    @Bean
    public AerospikeClient client(){
        return new AerospikeClient("hostname", 3000);

    }

}
