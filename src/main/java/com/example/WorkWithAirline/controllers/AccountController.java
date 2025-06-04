package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.AppUserRepository;
import com.example.WorkWithAirline.models.AppUser;
import com.example.WorkWithAirline.models.Dto.RegisterDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {
    @Autowired
    private AppUserRepository userRepo;

    @GetMapping("/register")
    public String register(Model model){
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success",false);
        return "register";
    }
    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute RegisterDto registerDto, BindingResult result) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(
                    new FieldError("registerDto", "email", "Email address already used")
            );
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();

            AppUser newUser = new AppUser();
            newUser.setUsername(registerDto.getUsername());
            newUser.setEmail(registerDto.getEmail());
            newUser.setRole("user");
            newUser.setPassword(bCryptEncoder.encode(registerDto.getPassword()));

            userRepo.save(newUser);

            model.addAttribute("registerDto", new RegisterDto());
            model.addAttribute("success",true);

        } catch (Exception ex){
            result.addError(
                    new FieldError("registerDto","username", ex.getMessage())
            );
        }

        return "register";
    }
}
