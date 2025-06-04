package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.PersonalRepository;
import com.example.WorkWithAirline.models.Dto.PersonalDto;
import com.example.WorkWithAirline.models.Personal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/personals")
public class PersonalController {
    @Autowired
    private PersonalRepository personalRepo;

    @GetMapping({"","/"})
    public String getPersonal(Model model) {
        var personals = personalRepo.findAll();
        model.addAttribute("personals",personals);
        return "personals/index";
    }
    @GetMapping("/create")
    public String createPersonal(Model model){
        PersonalDto personalDto = new PersonalDto();
        model.addAttribute("personalDto",personalDto);
        return "personals/create";
    }
    @PostMapping("/create")
    public String createPersonal(@Valid @ModelAttribute PersonalDto personalDto, BindingResult result) {
//        if (personalRepo.findById(personalDto.getId()) != null) {
//            result.addError(
//                    new FieldError("personalDto","id", personalDto.getId(), false, null, null, "Id already used"));
//        }
//        if (result.hasErrors()) {
//            return"personals/create";
//        }

        Personal personal = new Personal();
        personal.setSalary(personalDto.getSalary());
        personal.setType(personalDto.getType());

        personalRepo.save(personal);
        return "redirect:/personals";
    }
    @GetMapping("/edit")
    public String editPersonal(Model model, @RequestParam int id) {
        Personal personal = personalRepo.findById(id).orElse(null);
        if (personal == null) {
            return "redirect:/personals";
        }
        PersonalDto personalDto = new PersonalDto();
        personalDto.setId(personal.getId());
        personalDto.setSalary(personal.getSalary());
        personalDto.setType(personal.getType());

        model.addAttribute("personal",personal);
        model.addAttribute("personalDto",personalDto);

        return "personals/edit";
    }
    @PostMapping("/edit")
    public String editPersonal(Model model,@RequestParam int id, @Valid @ModelAttribute PersonalDto personalDto, BindingResult result) {
        Personal personal = personalRepo.findById(id).orElse(null);
        if (personal == null) {
            return "redirect:/personal";
        }
        model.addAttribute("personal",personal);
        if (result.hasErrors()) {
            return "personals/edit";
        }
        personal.setSalary(personalDto.getSalary());
        personal.setType(personalDto.getType());

        try{
            personalRepo.save(personal);
        }catch (Exception ex){
            result.addError(new FieldError("personalDto","id", personalDto.getId(), false, null, null, "Id already used"));

        }

        return "redirect:/personals";
    }
    @GetMapping("/delete")
    public String deletePersonal(@RequestParam int id){
        Personal personal = personalRepo.findById(id).orElse(null);
        if (personal!=null){
            personalRepo.delete(personal);
        }
        return "redirect:/personals";
    }
}
