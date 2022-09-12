package com.example.ipmanager.IPAddress;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class IPAddressConfig {

    @Bean
    CommandLineRunner commandLineRunner(IPAddressRepository repository) {
        return args -> {
            IPAddress zero = new IPAddress("10.0.0.0", "acquired");
            IPAddress one = new IPAddress("10.0.0.1", "acquired");
            IPAddress two = new IPAddress("10.0.0.2", "acquired");
            IPAddress three = new IPAddress("10.0.0.3", "available");
            IPAddress four = new IPAddress("10.0.0.4", "available");
            IPAddress five = new IPAddress("10.0.0.5", "available");

            repository.saveAll(List.of(zero, one, two, three, four, five));
        };
    }
}
