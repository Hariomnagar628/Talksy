package com.audio.ToText.Controller;

import com.audio.ToText.Model.User;
import com.audio.ToText.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Show register page
    @GetMapping("/register")
    public String registerPage() {
        return "register";   // register.html
    }

    // Handle register form submit
    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {

        // ✅ Password rule: min 8 chars, 1 letter, 1 digit
        String pwdRegex = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";

        if (!password.matches(pwdRegex)) {
            model.addAttribute("error",
                    "Password must be at least 8 characters long and contain at least one letter and one number");
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            return "register"; // stay on same page and show error
        }

        // ✅ Check if email already exists
        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error", "User with this email already exists");
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            return "register";
        }

        // ✅ Create and save user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // encode AFTER validation

        userRepository.save(user);

        // After success, go to login page
        return "redirect:/auth/login";
    }

    // Show login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";   // login.html
    }
}
