package com.booking.repository;

import java.util.Optional;

import com.booking.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	Optional<Users> findByUsername(String username);

	boolean existsByEmail(String email);
}
