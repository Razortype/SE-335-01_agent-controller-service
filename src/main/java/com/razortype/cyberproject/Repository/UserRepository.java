package com.razortype.cyberproject.Repository;

import com.razortype.cyberproject.core.enums.Role;
import com.razortype.cyberproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> getUserById(int userId);
    Optional<User> getUserByEmail(String email);

    List<User> getAllByRole(Role role);
}