package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.WayRepository;
import com.example.WorkWithAirline.models.AppUser;
import com.example.WorkWithAirline.models.Dto.AppUserDto;
import com.example.WorkWithAirline.models.Dto.WayDto;
import com.example.WorkWithAirline.models.Way;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ways")
public class WayController {
    @Autowired
    private WayRepository wayRepo;

    @GetMapping({"","/home"})
    public String getWays(Model model) {
        var ways = wayRepo.findAll();
        model.addAttribute("ways",ways);
        return "ways/index";
    }
    @GetMapping("/create")
    public String createWay(Model model){
        WayDto wayDto = new WayDto();
        model.addAttribute("wayDto",wayDto);
        return "ways/create";
    }
    @PostMapping("/create")
    public String createWay(@Valid @ModelAttribute WayDto wayDto) {

        Way way = new Way();
        way.setAddress(wayDto.getAddress());
        way.setAirplaneId(wayDto.getAirplaneId());
        way.setTypeOfClass(wayDto.getTypeOfClass());
        way.setRouteId(wayDto.getRouteId());
        way.setPrice(wayDto.getPrice());
        way.setDepartureTime(wayDto.getDepartureTime());
        wayRepo.save(way);
        return "redirect:/ways";
    }
    @GetMapping("/edit")
    public String editWay(Model model, @RequestParam int id) {
        Way way = wayRepo.findById(id).orElse(null);
        if (way == null) {
            return "redirect:/ways";
        }
        WayDto wayDto = new WayDto();
        wayDto.setAddress(way.getAddress());
        wayDto.setAirplaneId(way.getAirplaneId());
        wayDto.setTypeOfClass(way.getTypeOfClass());
        wayDto.setRouteId(way.getRouteId());
        wayDto.setPrice(way.getPrice());
        wayDto.setDepartureTime(way.getDepartureTime());

        model.addAttribute("way",way);
        model.addAttribute("wayDto",wayDto);

        return "ways/edit";
    }
    @PostMapping("/edit")
    public String editWay(Model model, @RequestParam int id, @Valid @ModelAttribute WayDto wayDto, BindingResult result) {
        Way way = wayRepo.findById(id).orElse(null);
        if (way == null) {
            return "redirect:/ways";
        }
        model.addAttribute("way",way);
        if (result.hasErrors()) {
            return "ways/edit";
        }
        way.setAddress(wayDto.getAddress());
        way.setAirplaneId(wayDto.getAirplaneId());
        way.setTypeOfClass(wayDto.getTypeOfClass());
        way.setRouteId(wayDto.getRouteId());
        way.setPrice(wayDto.getPrice());
        way.setDepartureTime(wayDto.getDepartureTime());
        try{
            wayRepo.save(way);
        }catch (Exception ex){
            result.addError(new FieldError("wayRepo","routeId", wayDto.getRouteId(), false, null, null, "Id already used"));

        }

        return "redirect:/ways";
    }
    @GetMapping("/delete")
    public String deleteWay(@RequestParam int id){
        Way way = wayRepo.findById(id).orElse(null);
        if (way!=null){
            wayRepo.delete(way);
        }
        return "redirect:/ways";
    }
}
