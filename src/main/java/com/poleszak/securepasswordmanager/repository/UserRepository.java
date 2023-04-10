package com.poleszak.securepasswordmanager.repository;

import com.poleszak.securepasswordmanager.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
