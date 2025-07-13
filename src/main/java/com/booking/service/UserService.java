package com.booking.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.booking.entity.Role;
import com.booking.entity.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booking.repository.RoleRepository;
import com.booking.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users registerUser(Users user, String roleName) {
        // Set the password and roles
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role selectedRole = roleRepository.findByName(roleName);
        Set<Role> roles = new HashSet<>();
        roles.add(selectedRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
