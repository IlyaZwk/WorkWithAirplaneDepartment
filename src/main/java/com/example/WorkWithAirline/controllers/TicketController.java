package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.TicketRepository;
import com.example.WorkWithAirline.models.Dto.TicketDto;
import com.example.WorkWithAirline.models.Ticket;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepo;

    @GetMapping({"","/"})
    public String getTickets(Model model) {
        var tickets = ticketRepo.findAll();
        model.addAttribute("tickets",tickets);
        return "tickets/index";
    }
    @GetMapping("/create")
    public String createTicket(Model model){
        TicketDto ticketDto = new TicketDto();
        model.addAttribute("ticketDto",ticketDto);
        return "tickets/create";
    }
    @PostMapping("/create")
    public String createTicket( @ModelAttribute TicketDto ticketDto, BindingResult result) {
        if (ticketRepo.findByPassport(ticketDto.getPassport()) != null) {
            result.addError(
                    new FieldError("ticketDto","passport", ticketDto.getPassport(), false, null, null, "Passport already used"));
        }
        if (result.hasErrors()) {
            return"tickets/create";
        }

        Ticket ticket = new Ticket();
        ticket.setPassengerId(ticketDto.getPassengerId());
        ticket.setPassport(ticketDto.getPassport());
        ticket.setTypeOfClass(ticketDto.getTypeOfClass());
        ticket.setAirplaneId(ticketDto.getAirplaneId());
        ticket.setPrice(ticketDto.getPrice());

        ticketRepo.save(ticket);
        return "redirect:/tickets";
    }
    @GetMapping("/edit")
    public String editTicket(Model model, @RequestParam int id) {
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket == null) {
            return "redirect:/tickets";
        }
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setPrice(ticket.getPrice());
        ticketDto.setPassport(ticket.getPassport());
        ticketDto.setAirplaneId(ticket.getAirplaneId());
        ticketDto.setTypeOfClass(ticket.getTypeOfClass());
        ticketDto.setPassengerId(ticket.getPassengerId());

        model.addAttribute("ticket",ticket);
        model.addAttribute("ticketDto",ticketDto);

        return "tickets/edit";
    }
    @PostMapping("/edit")
    public String editTicket(Model model,@RequestParam int id,  @ModelAttribute TicketDto ticketDto, BindingResult result) {
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket == null) {
            return "redirect:/tickets";
        }
        model.addAttribute("ticket",ticket);
        if (result.hasErrors()) {
            return "tickets/edit";
        }
        ticket.setPrice(ticketDto.getPrice());
        ticket.setPassport(ticketDto.getPassport());
        ticket.setAirplaneId(ticketDto.getAirplaneId());
        ticket.setTypeOfClass(ticketDto.getTypeOfClass());
        ticket.setPassengerId(ticketDto.getPassengerId());

        try{
            ticketRepo.save(ticket);
        }catch (Exception ex){
            result.addError(new FieldError("ticketDto","passport", ticketDto.getPassport(), false, null, null, "Passport already used"));

        }

        return "redirect:/tickets";
    }
    @GetMapping("/delete")
    public String deleteTicket(@RequestParam int id){
        Ticket ticket = ticketRepo.findById(id).orElse(null);
        if (ticket!=null){
            ticketRepo.delete(ticket);
        }
        return "redirect:/tickets";
    }
}
