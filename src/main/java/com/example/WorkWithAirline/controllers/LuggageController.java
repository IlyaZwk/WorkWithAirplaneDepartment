package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.LuggageRepository;
import com.example.WorkWithAirline.models.Dto.LuggageDto;
import com.example.WorkWithAirline.models.Luggage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/luggages")
public class LuggageController {
    @Autowired
    private LuggageRepository luggageRepo;

    @GetMapping({"","/"})
    public String getLuggages(Model model) {
        var luggages = luggageRepo.findAll();
        model.addAttribute("luggages",luggages);
        return "luggages/index";
    }
    @GetMapping("/create")
    public String createLuggage(Model model){
        LuggageDto luggageDto = new LuggageDto();
        model.addAttribute("luggageDto",luggageDto);
        return "luggages/create";
    }
    @PostMapping("/create")
    public String createLuggage(@Valid @ModelAttribute LuggageDto luggageDto, BindingResult result) {
//        if (luggageRepo.findById(luggageDto.getId()) != null) {
//            result.addError(
//                    new FieldError("luggageDto","id", luggageDto.getId(), false, null, null, "Id already used"));
//        }
//        if (result.hasErrors()) {
//            return"luggages/create";
//        }

        Luggage luggage = new Luggage();
        luggage.setWeight(luggageDto.getWeight());

        luggageRepo.save(luggage);
        return "redirect:/luggages";
    }
    @GetMapping("/edit")
    public String editLuggage(Model model, @RequestParam int id) {
        Luggage luggage = luggageRepo.findById(id).orElse(null);
        if (luggage == null) {
            return "redirect:/luggages";
        }
        LuggageDto luggageDto = new LuggageDto();
        luggageDto.setId(luggage.getId());
        luggageDto.setWeight(luggage.getWeight());

        model.addAttribute("luggage",luggage);
        model.addAttribute("luggageDto",luggageDto);

        return "luggages/edit";
    }
    @PostMapping("/edit")
    public String editLuggage(Model model,@RequestParam int id, @Valid @ModelAttribute LuggageDto luggageDto, BindingResult result) {
        Luggage luggage = luggageRepo.findById(id).orElse(null);
        if (luggage == null) {
            return "redirect:/luggages";
        }
        model.addAttribute("luggage",luggage);
        if (result.hasErrors()) {
            return "luggages/edit";
        }

        luggage.setWeight(luggageDto.getWeight());

        try{
            luggageRepo.save(luggage);
        }catch (Exception ex){
            result.addError(new FieldError("luggageDto","id", luggageDto.getId(), false, null, null, "Id already used"));

        }

        return "redirect:/luggages";
    }
    @GetMapping("/delete")
    public String deleteLuggage(@RequestParam int id){
        Luggage luggage = luggageRepo.findById(id).orElse(null);
        if (luggage!=null){
            luggageRepo.delete(luggage);
        }
        return "redirect:/luggages";
    }
}
