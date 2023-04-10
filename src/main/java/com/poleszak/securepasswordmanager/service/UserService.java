package com.poleszak.securepasswordmanager.service;

import com.poleszak.securepasswordmanager.model.dto.UserDto;
import com.poleszak.securepasswordmanager.model.entity.User;
import com.poleszak.securepasswordmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

import static org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${security.password.iterations}")
    private final int ITERATIONS;

    @Value("${security.password.salt-length}")
    private final int SALT_LENGTH;

    @Value("${security.password.secret}")
    private final String SECRET;

    private final UserRepository userRepository;

    public User register(UserDto userDto) {
        var keyGenerator = KeyGenerators.secureRandom(SALT_LENGTH);
        var saltBytes = keyGenerator.generateKey();
        var salt = Base64.getEncoder().encodeToString(saltBytes);

        var passwordEncoder = new Pbkdf2PasswordEncoder(SECRET, SALT_LENGTH, ITERATIONS, PBKDF2WithHmacSHA256);
        passwordEncoder.setEncodeHashAsBase64(true);
        var passwordHash = passwordEncoder.encode(userDto.password());

        var user = User.builder()
                .username(userDto.username())
                .passwordHash(passwordHash)
                .salt(salt)
                .build();

        return userRepository.save(user);
    }

    public boolean verifyPassword(String username, String password) {
        var user = userRepository.findByUsername(username);
        validateUser(user);

        Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(SECRET, SALT_LENGTH, ITERATIONS, PBKDF2WithHmacSHA256);
        passwordEncoder.setEncodeHashAsBase64(true);
        return passwordEncoder.matches(password, user.getPasswordHash());
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
    }
}
