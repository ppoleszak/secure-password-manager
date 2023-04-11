package com.poleszak.securepasswordmanager.controller;

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
        return "login";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "login";
        }

        log.info("Start registration for user: {}", userDto.getUsername());
        userService.register(userDto);
        log.info("Registration successful for user: {}", userDto.getUsername());

        return "redirect:/";
    }


    @PostMapping("/verify")
    public String verifyPassword(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "login";
        }

        var isUserVerified = userService.verifyPassword(userDto);

        if (isUserVerified) {
            return "redirect:/dashboard";
        } else {
            result.rejectValue("password", "error.userDto", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
