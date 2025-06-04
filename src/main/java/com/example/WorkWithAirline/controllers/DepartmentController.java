package com.example.WorkWithAirline.controllers;

import com.example.WorkWithAirline.Repositories.DepartmentRepository;
import com.example.WorkWithAirline.models.Department;
import com.example.WorkWithAirline.models.Dto.DepartmentDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepo;

    @GetMapping({"","/"})
    public String getDepartment(Model model) {
        var departments = departmentRepo.findAll();
        model.addAttribute("departments",departments);
        return "departments/index";
    }
    @GetMapping("/create")
    public String createDepartment(Model model){
        DepartmentDto departmentDto = new DepartmentDto();
        model.addAttribute("departmentDto",departmentDto);
        return "departments/create";
    }
    @PostMapping("/create")
    public String createDepartment(@Valid @ModelAttribute DepartmentDto departmentDto, BindingResult result) {
        if (departmentRepo.findByAddress(departmentDto.getAddress()) != null) {
            result.addError(
                    new FieldError("departmentDto","address", departmentDto.getAddress(), false, null, null, "Address already used"));
        }
        if (result.hasErrors()) {
            return"departments/create";
        }

        Department department = new Department();
        department.setAddress(departmentDto.getAddress());
        department.setDepartureTimeOfAirplane(departmentDto.getDepartureTimeOfAirplane());

        departmentRepo.save(department);
        return "redirect:/departments";
    }
    @GetMapping("/edit")
    public String editDepartment(Model model, @RequestParam int id) {
        Department department = departmentRepo.findById(id).orElse(null);
        if (department == null) {
            return "redirect:/department";
        }
        DepartmentDto departmentDto = new DepartmentDto();
        department.setId(departmentDto.getId());
        departmentDto.setAddress(department.getAddress());
        departmentDto.setDepartureTimeOfAirplane(department.getDepartureTimeOfAirplane());

        model.addAttribute("department",department);
        model.addAttribute("departmentDto",departmentDto);

        return "departments/edit";
    }
    @PostMapping("/edit")
    public String editDepartment(Model model,@RequestParam int id, @Valid @ModelAttribute DepartmentDto departmentDto, BindingResult result) {
        Department department = departmentRepo.findById(id).orElse(null);
        if (department == null) {
            return "redirect:/departments";
        }
        model.addAttribute("department",department);
        if (result.hasErrors()) {
            return "departments/edit";
        }
        department.setAddress(departmentDto.getAddress());
        department.setDepartureTimeOfAirplane(departmentDto.getDepartureTimeOfAirplane());

        try{
            departmentRepo.save(department);
        }catch (Exception ex){
            result.addError(new FieldError("departmentDto","address", departmentDto.getAddress(), false, null, null, "Address already used"));

        }

        return "redirect:/departments";
    }
    @GetMapping("/delete")
    public String deleteDepartment(@RequestParam int id){
        Department department = departmentRepo.findById(id).orElse(null);
        if (department!=null){
            departmentRepo.delete(department);
        }
        return "redirect:/departments";
    }
}
