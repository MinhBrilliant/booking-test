package com.booking.repository;

import java.util.Optional;

import com.booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

	boolean existsByEmail(String email);
}
