package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.AppUserRepository;
import com.example.WorkWithAirline.Repositories.WayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private WayRepository wayRepo;

    @Autowired
    private AppUserRepository userRepo;
    @GetMapping("/")
    public String mainPage() {
        return "main";
    }
    @GetMapping("/home")
    public String home(Model model) {
        var ways = wayRepo.findAll();
        model.addAttribute("ways",ways);
        return "mainAuthenticated";
    }
    @GetMapping("/adm")
    public String adm() {
        return "index";
    }
    @GetMapping("/profiles")
    public String profile(Model model){
        var perm =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepo.findByUsername(perm.getUsername());
        model.addAttribute("user",user);
        return "profile";
    }

}
