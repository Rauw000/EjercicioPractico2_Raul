package com.ejercicioPractico2.plataformaAcademica.Controller;

import com.ejercicioPractico2.plataformaAcademica.Service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService u) {
        this.userService = u;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails ud, Model m) {
        userService.findByUsername(ud.getUsername()).ifPresent(user -> m.addAttribute("user", user));
        return "profile";
    }
}
