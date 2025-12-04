package com.ejercicioPractico2.plataformaAcademica.Controller;

import com.ejercicioPractico2.plataformaAcademica.Domain.User;
import com.ejercicioPractico2.plataformaAcademica.Service.RoleService;
import com.ejercicioPractico2.plataformaAcademica.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminUserController(UserService u, RoleService r, PasswordEncoder enc) {
        this.userService = u;
        this.roleService = r;
        this.passwordEncoder = enc;
    }

    // LISTA DE USUARIOS
    @GetMapping("/usuarios")
    public String list(Model m) {
        m.addAttribute("usuarios", userService.findAll());
        return "admin/usuarios";
    }

    // FORMULARIO DE CREACIÓN
    @GetMapping("/usuarios/new")
    public String createForm(Model m) {
        m.addAttribute("user", new User());
        m.addAttribute("roles", roleService.findAll());
        return "admin/user_form";
    }

    // GUARDAR (CREAR / EDITAR)
    @PostMapping("/usuarios")
    public String save(
            @ModelAttribute("user") User user,
            @RequestParam("roleId") Long roleId,
            Model model
    ) {

        boolean editing = (user.getId() != null);

        // =================================================
        // VALIDAR EMAIL DUPLICADO
        // =================================================
        User existingByEmail = userService.findByEmail(user.getEmail());

        if (existingByEmail != null) {
            if (!editing || !existingByEmail.getId().equals(user.getId())) {
                model.addAttribute("user", user);
                model.addAttribute("roles", roleService.findAll());
                model.addAttribute("error", "El email ya está registrado");
                return "admin/user_form";
            }
        }

        // =================================================
        // MANEJO DE CONTRASEÑA
        // =================================================
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else if (editing) {
            // Mantener la contraseña existente
            userService.findById(user.getId())
                    .ifPresent(u -> user.setPassword(u.getPassword()));
        }

        // =================================================
        // ASIGNAR ROL
        // =================================================
        roleService.findById(roleId).ifPresent(user::setRole);

        // =================================================
        // FECHA DE CREACIÓN SOLO AL CREAR
        // =================================================
        if (!editing) {
            user.setCreatedAt(LocalDateTime.now());
        }

        // GUARDAR
        userService.save(user);
        return "redirect:/admin/usuarios";
    }

    // FORMULARIO DE EDICIÓN
    @GetMapping("/usuarios/edit/{id}")
    public String edit(@PathVariable Long id, Model m) {

        User user = userService.findById(id).orElse(null);

        if (user == null) {
            return "redirect:/admin/usuarios";
        }

        m.addAttribute("user", user);
        m.addAttribute("roles", roleService.findAll());
        return "admin/user_form";
    }

    // ELIMINAR
    @PostMapping("/usuarios/delete/{id}")
    public String delete(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/usuarios";
    }

    // DETALLE DE USUARIO
    @GetMapping("/usuarios/{id}")
    public String detail(@PathVariable Long id, Model m) {
        userService.findById(id).ifPresent(u -> m.addAttribute("user", u));
        return "admin/user_detail";
    }

    // CONSULTAS AVANZADAS
    @GetMapping("/consultas")
    public String consultas(Model m,
            @RequestParam(value = "rol", required = false) String rol,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "end", required = false) String end) {

        if (rol != null && !rol.isBlank()) {
            m.addAttribute("resultados", userService.findByRoleNombre(rol));

        } else if (q != null && !q.isBlank()) {
            m.addAttribute("resultados", userService.searchByNombreOrEmail(q));

        } else if (start != null && end != null) {
            m.addAttribute("resultados",
                    userService.findByCreatedBetween(LocalDateTime.parse(start), LocalDateTime.parse(end)));

        } else {
            m.addAttribute("resultados", userService.findAllOrderByCreatedAtDesc());
        }

        m.addAttribute("roles", roleService.findAll());
        m.addAttribute("countActive", userService.countActive());
        m.addAttribute("countInactive", userService.countInactive());
        return "admin/consultas";
    }
}
