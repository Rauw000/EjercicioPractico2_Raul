package com.ejercicioPractico2.plataformaAcademica.Controller;

import com.ejercicioPractico2.plataformaAcademica.Domain.Role;
import com.ejercicioPractico2.plataformaAcademica.Service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/roles")
public class AdminRoleController {

    private final RoleService roleService;

    public AdminRoleController(RoleService r) {
        this.roleService = r;
    }

    @GetMapping
    public String list(Model m) {
        m.addAttribute("roles", roleService.findAll());
        return "admin/roles";
    }

    @GetMapping("/new")
    public String newForm(Model m) {
        m.addAttribute("role", new Role());
        return "admin/role_form";
    }

    @PostMapping
    public String save(@ModelAttribute Role role) {
        roleService.save(role);
        return "redirect:/admin/roles";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model m) {
        roleService.findById(id).ifPresent(r -> m.addAttribute("role", r));
        return "admin/role_form";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        roleService.deleteById(id);
        return "redirect:/admin/roles";
    }
}
