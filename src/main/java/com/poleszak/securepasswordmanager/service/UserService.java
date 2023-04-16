package com.poleszak.securepasswordmanager.service;

import com.poleszak.securepasswordmanager.exception.UserNotFoundException;
import com.poleszak.securepasswordmanager.model.dto.UserDto;
import com.poleszak.securepasswordmanager.model.entity.UserApp;
import com.poleszak.securepasswordmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Base64.getEncoder;
import static org.springframework.security.crypto.keygen.KeyGenerators.secureRandom;
import static org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${security.password.iterations}")
    private int ITERATIONS;

    @Value("${security.password.salt-length}")
    private int SALT_LENGTH;

    @Value("${security.password.secret}")
    private String SECRET;

    private final UserRepository userRepository;

    public void register(UserDto userDto) {
        var keyGenerator = secureRandom(SALT_LENGTH);
        var saltBytes = keyGenerator.generateKey();
        var salt = getEncoder().encodeToString(saltBytes);

        var passwordEncoder = new Pbkdf2PasswordEncoder(SECRET, SALT_LENGTH, ITERATIONS, PBKDF2WithHmacSHA256);
        passwordEncoder.setEncodeHashAsBase64(true);
        var passwordHash = passwordEncoder.encode(userDto.getPassword());

        var user = UserApp.builder()
                .username(userDto.getUsername())
                .passwordHash(passwordHash)
                .salt(salt)
                .build();

        userRepository.save(user);
    }

    public boolean verifyPassword(UserDto userDto) {
        var user = userRepository.findByUsername(userDto.getUsername());
        validateUser(user);

        var passwordEncoder = new Pbkdf2PasswordEncoder(SECRET, SALT_LENGTH, ITERATIONS, PBKDF2WithHmacSHA256);
        passwordEncoder.setEncodeHashAsBase64(true);

        return passwordEncoder.matches(userDto.getPassword(), user.getPasswordHash());
    }

    private void validateUser(UserApp user) {
        if (user == null) {
            throw new UserNotFoundException("User not found.");
        }
    }
}