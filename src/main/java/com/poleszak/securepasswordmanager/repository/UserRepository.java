package com.poleszak.securepasswordmanager.repository;

import com.poleszak.securepasswordmanager.model.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserApp, Long> {

    UserApp findByUsername(String username);
}
