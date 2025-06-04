package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.AirplaneRepository;
import com.example.WorkWithAirline.models.Airplane;
import com.example.WorkWithAirline.models.Dto.AirplaneDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/airplanes")
public class AirplaneController {
    @Autowired
    private AirplaneRepository airplaneRepo;

    @GetMapping({"","/"})
    public String getAirplane(Model model) {
        var airplanes = airplaneRepo.findAll();
        model.addAttribute("airplanes",airplanes);
        return "airplanes/index";
    }
    @GetMapping("/create")
    public String createAirplane(Model model){
        AirplaneDto airplaneDto = new AirplaneDto();
        model.addAttribute("airplaneDto",airplaneDto);
        return "airplanes/create";
    }
    @PostMapping("/create")
    public String createAirplane(@Valid @ModelAttribute AirplaneDto airplaneDto, BindingResult result) {
//        if (airplaneRepo.findById(airplaneDto.getId()) != null) {
//            result.addError(
//                    new FieldError("airplaneDto","id", airplaneDto.getId(), false, null, null, "Id already used"));
//        }
//        if (result.hasErrors()) {
//            return"airplanes/create";
//        }

        Airplane airplane = new Airplane();
        airplane.setRouteId(airplaneDto.getRouteId());

        airplaneRepo.save(airplane);
        return "redirect:/airplanes";
    }
    @GetMapping("/edit")
    public String editAirplane(Model model, @RequestParam int id) {
        Airplane airplane = airplaneRepo.findById(id).orElse(null);
        if (airplane == null) {
            return "redirect:/airplanes";
        }
        AirplaneDto airplaneDto = new AirplaneDto();
        airplaneDto.setId(airplane.getId());
        airplaneDto.setRouteId(airplane.getRouteId());

        model.addAttribute("airplane",airplane);
        model.addAttribute("airplaneDto",airplaneDto);

        return "airplanes/edit";
    }
    @PostMapping("/edit")
    public String editAirplane(Model model,@RequestParam int id, @Valid @ModelAttribute AirplaneDto airplaneDto, BindingResult result) {
        Airplane airplane = airplaneRepo.findById(id).orElse(null);
        if (airplane == null) {
            return "redirect:/airplanes";
        }
        model.addAttribute("airplane",airplane);
        if (result.hasErrors()) {
            return "airplanes/edit";
        }

        airplane.setRouteId(airplaneDto.getRouteId());

        try{
            airplaneRepo.save(airplane);
        }catch (Exception ex){
            result.addError(new FieldError("airplaneDto","id", airplaneDto.getId(), false, null, null, "Id already used"));

        }

        return "redirect:/airplanes";
    }
    @GetMapping("/delete")
    public String deleteAirplane(@RequestParam int id){
        Airplane airplane = airplaneRepo.findById(id).orElse(null);
        if (airplane!=null){
            airplaneRepo.delete(airplane);
        }
        return "redirect:/airplanes";
    }
}
