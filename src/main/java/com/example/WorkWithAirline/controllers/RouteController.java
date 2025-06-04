package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.RouteRepository;
import com.example.WorkWithAirline.models.Dto.RouteDto;
import com.example.WorkWithAirline.models.Route;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/routes")
public class RouteController {
    @Autowired
    private RouteRepository routeRepo;

    @GetMapping({"","/"})
    public String getRoutes(Model model) {
        var routes = routeRepo.findAll();
        model.addAttribute("routes",routes);
        return "routes/index";
    }
    @GetMapping("/create")
    public String createRoute(Model model){
        RouteDto routeDto = new RouteDto();
        model.addAttribute("routeDto",routeDto);
        return "routes/create";
    }
    @PostMapping("/create")
    public String createRoute(@Valid @ModelAttribute RouteDto routeDto, BindingResult result) {
//        if (routeRepo.findById(routeDto.getId()) != null) {
//            result.addError(
//                    new FieldError("routeDto","id", routeDto.getId(), false, null, null, "Id already used"));
//        }
//        if (result.hasErrors()) {
//            return"routes/create";
//        }

        Route route = new Route();
        route.setArrivalTime(routeDto.getArrivalTime());
        route.setDepartureTime(routeDto.getDepartureTime());
        route.setArrivalLocationId(routeDto.getArrivalLocationId());
        route.setDepartureLocationId(routeDto.getDepartureLocationId());

        routeRepo.save(route);
        return "redirect:/routes";
    }
    @GetMapping("/edit")
    public String editRoute(Model model, @RequestParam int id) {
        Route route = routeRepo.findById(id).orElse(null);
        if (route == null) {
            return "redirect:/routes";
        }
        RouteDto routeDto = new RouteDto();
        routeDto.setId(route.getId());
        routeDto.setArrivalTime(route.getArrivalTime());
        routeDto.setDepartureTime(route.getDepartureTime());
        routeDto.setArrivalLocationId(route.getArrivalLocationId());
        routeDto.setDepartureLocationId(route.getDepartureLocationId());

        model.addAttribute("route",route);
        model.addAttribute("routeDto",routeDto);

        return "routes/edit";
    }
    @PostMapping("/edit")
    public String editRoute(Model model,@RequestParam int id, @Valid @ModelAttribute RouteDto routeDto, BindingResult result) {
        Route route = routeRepo.findById(id).orElse(null);
        if (route == null) {
            return "redirect:/routes";
        }
        model.addAttribute("route",route);
        if (result.hasErrors()) {
            return "routes/edit";
        }
        route.setArrivalTime(routeDto.getArrivalTime());
        route.setDepartureTime(routeDto.getDepartureTime());
        route.setArrivalLocationId(routeDto.getArrivalLocationId());
        route.setDepartureLocationId(routeDto.getDepartureLocationId());

        try{
            routeRepo.save(route);
        }catch (Exception ex){
            result.addError(new FieldError("routeDto","id", routeDto.getId(), false, null, null, "Id already used"));

        }

        return "redirect:/routes";
    }
    @GetMapping("/delete")
    public String deleteRoute(@RequestParam int id){
        Route route = routeRepo.findById(id).orElse(null);
        if (route!=null){
            routeRepo.delete(route);
        }
        return "redirect:/routes";
    }
}
