package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.PassengerRepository;
import com.example.WorkWithAirline.models.Dto.PassengerDto;
import com.example.WorkWithAirline.models.Passenger;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    @Autowired
    private PassengerRepository passengerRepo;

    @GetMapping({"","/"})
    public String getPassengers(Model model) {
        var passengers = passengerRepo.findAll();
        model.addAttribute("passengers",passengers);
        return "passengers/index";
    }
    @GetMapping("/create")
    public String createPassenger(Model model){
        PassengerDto passengerDto = new PassengerDto();
        model.addAttribute("PassengerDto",passengerDto);
        return "passengers/create";
    }
    @PostMapping("/create")
    public String createPassenger(@Valid @ModelAttribute PassengerDto passengerDto, BindingResult result) {
        if (passengerRepo.findByPassport(passengerDto.getPassport()) != null) {
            result.addError(
                    new FieldError("passengerDto","passport", passengerDto.getPassport(), false, null, null, "Passport already used"));
        }
        if (result.hasErrors()) {
            return"passengers/create";
        }

        Passenger passenger = new Passenger();
        passenger.setName(passengerDto.getName());
        passenger.setPassport(passengerDto.getPassport());

        passengerRepo.save(passenger);
        return "redirect:/passengers";
    }
    @GetMapping("/edit")
    public String editPassenger(Model model, @RequestParam int id) {
        Passenger passenger = passengerRepo.findById(id).orElse(null);
        if (passenger == null) {
            return "redirect:/passengers";
        }
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setId(passenger.getId());
        passengerDto.setName(passenger.getName());
        passengerDto.setPassport(passenger.getPassport());

        model.addAttribute("passenger",passenger);
        model.addAttribute("passengerDto",passengerDto);

        return "passengers/edit";
    }
    @PostMapping("/edit")
    public String editPassenger(Model model,@RequestParam int id, @Valid @ModelAttribute PassengerDto passengerDto, BindingResult result) {
        Passenger passenger = passengerRepo.findById(id).orElse(null);
        if (passenger == null) {
            return "redirect:/passengers";
        }
        model.addAttribute("passenger",passenger);
        if (result.hasErrors()) {
            return "passengers/edit";
        }
        passenger.setName(passengerDto.getName());
        passenger.setPassport(passengerDto.getPassport());

        try{
            passengerRepo.save(passenger);
        }catch (Exception ex){
            result.addError(new FieldError("passengerDto","passport", passengerDto.getPassport(), false, null, null, "Passport already used"));

        }

        return "redirect:/passengers";
    }
    @GetMapping("/delete")
    public String deletePassenger(@RequestParam int id){
        Passenger passenger = passengerRepo.findById(id).orElse(null);
        if (passenger!=null){
            passengerRepo.delete(passenger);
        }
        return "redirect:/passengers";
    }

}
