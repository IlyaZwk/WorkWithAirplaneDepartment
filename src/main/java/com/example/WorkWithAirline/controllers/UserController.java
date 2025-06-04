package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.AppUserRepository;
import com.example.WorkWithAirline.models.Dto.AppUserDto;
import com.example.WorkWithAirline.models.AppUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AppUserRepository userRepo;
    @Autowired
    public PasswordEncoder passwordEncoder;



    @GetMapping({"","/"})
    public String getUsers(Model model) {
        var users = userRepo.findAll();
        model.addAttribute("users",users);
        return "users/index";
    }
    @GetMapping("/create")
    public String createUser(Model model){
        AppUserDto userDto = new AppUserDto();
        model.addAttribute("userDto",userDto);
        return "users/create";
    }
    @PostMapping("/create")
    public String createUser(@Valid @ModelAttribute AppUserDto userDto, BindingResult result) {
        if (userRepo.findByUsername(userDto.getUsername()) != null) {
            result.addError(
                    new FieldError("userDto","username", userDto.getUsername(), false, null, null, "Username already used"));
        }
        if (result.hasErrors()) {
            return"users/create";
        }

        AppUser user = new AppUser();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());

        userRepo.save(user);
        return "redirect:/users";
    }
    @GetMapping("/edit")
    public String editUser(Model model, @RequestParam int id) {
        AppUser user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/users";
        }
        AppUserDto userDto = new AppUserDto();
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());

        model.addAttribute("user",user);
        model.addAttribute("userDto",userDto);

        return "users/edit";
    }
    @PostMapping("/edit")
    public String editUser(Model model, @RequestParam int id, @Valid @ModelAttribute AppUserDto userDto, BindingResult result) {
        AppUser user = userRepo.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute("user",user);
        if (result.hasErrors()) {
            return "users/edit";
        }
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setRole(userDto.getRole());
        try{
            userRepo.save(user);
        }catch (Exception ex){
            result.addError(new FieldError("userDto","username", userDto.getUsername(), false, null, null, "Username already used"));

        }

        return "redirect:/users";
    }
    @GetMapping("/delete")
    public String deleteUser(@RequestParam int id){
        AppUser user = userRepo.findById(id).orElse(null);
        if (user!=null){
            userRepo.delete(user);
        }
        return "redirect:/users";
    }

}
