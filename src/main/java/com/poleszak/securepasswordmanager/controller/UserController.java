package com.poleszak.securepasswordmanager.controller;

import com.poleszak.securepasswordmanager.model.dto.UserDto;
import com.poleszak.securepasswordmanager.model.entity.User;
import com.poleszak.securepasswordmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestParam UserDto userDto) {
        log.info("Start registration user with username {}", userDto.username());
        var user = userService.register(userDto);
        log.info("Registration successful for user: {}", userDto.username());

        return ResponseEntity.status(CREATED).body(user);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyPassword(@RequestParam("username") String username, @RequestParam("password") String password) {
        var isUserVerified = userService.verifyPassword(username, password);
        return ResponseEntity.status(OK).body(isUserVerified);
    }
}