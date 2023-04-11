package com.poleszak.securepasswordmanager.controller;

import com.poleszak.securepasswordmanager.exception.UserNotFoundException;
import com.poleszak.securepasswordmanager.model.dto.UserDto;
import com.poleszak.securepasswordmanager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "All fields must be filled in.");
            return "register";
        }

        log.info("Start registration for user: {}", userDto.getUsername());
        userService.register(userDto);
        log.info("Registration successful for user: {}", userDto.getUsername());

        return "redirect:/";
    }

    @PostMapping("/verify")
    public String verifyPassword(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }

        try {
            var isUserVerified = userService.verifyPassword(userDto);

            if (isUserVerified) {
                return "redirect:/dashboard";
            } else {
                model.addAttribute("errorMessage", "User not found.");
                return "index";
            }
        } catch (UserNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "index";
        }
    }
}