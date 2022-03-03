package vccorp.els;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import vccorp.els.service.ELSService;

@Configuration
public class Runner implements CommandLineRunner {
    @Autowired
    private ELSService elsService;
    @Override
    public void run(String... args) throws Exception {
        elsService.incrementPrepTime("pasta", 1);
    }
}
