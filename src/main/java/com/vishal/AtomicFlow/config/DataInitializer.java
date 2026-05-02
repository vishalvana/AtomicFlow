package com.vishal.AtomicFlow.config;

import com.vishal.AtomicFlow.entity.Role;
import com.vishal.AtomicFlow.entity.User;
import com.vishal.AtomicFlow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {

            // check if admin already exists
            if (userRepository.findByEmail("Admin@test.com").isEmpty()) {

                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

                User admin = new User();
                admin.setName("ADMIN2");
                admin.setEmail("Admin@test.com");
                admin.setPassword(encoder.encode("Admin123"));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("✅ Default ADMIN created");
            }
        };
    }
}