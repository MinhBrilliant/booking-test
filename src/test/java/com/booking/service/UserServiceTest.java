package com.booking.service;

import com.booking.entity.Role;
import com.booking.entity.Users;
import com.booking.repository.RoleRepository;
import com.booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockUserRepository, mockRoleRepository, mockPasswordEncoder);
    }

    @Test
    void testRegisterUser() {
        // Setup
        final Users user = new Users();
        user.setId(0L);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");
        final Role role = new Role();
        user.setRoles(Set.of(role));

        when(mockPasswordEncoder.encode("password")).thenReturn("password");
        when(mockRoleRepository.findByName("roleName")).thenReturn(new Role("roleName"));

        // Configure UserRepository.save(...).
        final Users user1 = new Users();
        user1.setId(0L);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        final Role role1 = new Role();
        user1.setRoles(Set.of(role1));
        when(mockUserRepository.save(any(Users.class))).thenReturn(user1);

        // Run the test
        final Users result = userServiceUnderTest.registerUser(user, "roleName");

        // Verify the results
    }

    @Test
    void testFindByUsername() {
        // Setup
        // Configure UserRepository.findByUsername(...).
        final Users user1 = new Users();
        user1.setId(0L);
        user1.setUsername("username");
        user1.setPassword("password");
        user1.setEmail("email");
        final Role role = new Role();
        user1.setRoles(Set.of(role));
        final Optional<Users> user = Optional.of(user1);
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // Run the test
        final Users result = userServiceUnderTest.findByUsername("username");

        // Verify the results
    }

    @Test
    void testFindByUsername_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        // Run the test
        final Users result = userServiceUnderTest.findByUsername("username");

        // Verify the results
        assertThat(result).isNull();
    }

    @Test
    void testExistsByEmail() {
        // Setup
        when(mockUserRepository.existsByEmail("email")).thenReturn(false);

        // Run the test
        final boolean result = userServiceUnderTest.existsByEmail("email");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testExistsByEmail_UserRepositoryReturnsTrue() {
        // Setup
        when(mockUserRepository.existsByEmail("email")).thenReturn(true);

        // Run the test
        final boolean result = userServiceUnderTest.existsByEmail("email");

        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testGetAllRoles() {
        // Setup
        when(mockRoleRepository.findAll()).thenReturn(List.of(new Role("roleName")));

        // Run the test
        final List<Role> result = userServiceUnderTest.getAllRoles();

        // Verify the results
    }

    @Test
    void testGetAllRoles_RoleRepositoryReturnsNoItems() {
        // Setup
        when(mockRoleRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Role> result = userServiceUnderTest.getAllRoles();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
