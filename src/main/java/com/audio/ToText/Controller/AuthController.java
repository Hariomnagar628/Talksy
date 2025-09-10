package com.audio.ToText.Controller;

import com.audio.ToText.Model.User;
import com.audio.ToText.Repository.userrepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final userrepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(userrepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registration form (Thymeleaf or simple HTML)
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // create register.html
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "redirect:/login";
    }
}



