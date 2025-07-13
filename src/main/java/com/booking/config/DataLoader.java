package com.booking.config;

import com.booking.entity.Role;
import com.booking.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminRole = "ROLE_ADMIN";
        String userRole = "ROLE_USER";
        if(!roleRepository.existsByName(adminRole)){
            roleRepository.save(new Role(adminRole));
        }
        if(!roleRepository.existsByName(userRole)){
            roleRepository.save(new Role(userRole));
        }
    }
}
