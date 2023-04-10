package com.poleszak.securepasswordmanager.controller;

import com.poleszak.securepasswordmanager.model.dto.UserDto;
import com.poleszak.securepasswordmanager.model.entity.UserApp;
import com.poleszak.securepasswordmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserApp> register(@RequestBody UserDto userDto) {
        log.info("Start registration for user: {}", userDto.username());
        var user = userService.register(userDto);
        log.info("Registration successful for user: {}", userDto.username());

        return ResponseEntity.status(CREATED).body(user);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyPassword(@RequestBody UserDto userDto) {
        var isUserVerified = userService.verifyPassword(userDto);

        return ResponseEntity.status(OK).body(isUserVerified);
    }
}