package com.ejercicioPractico2.plataformaAcademica.Service;

import com.ejercicioPractico2.plataformaAcademica.Repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public CustomUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.ejercicioPractico2.plataformaAcademica.Domain.User u = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No existe: " + username));
        String roleName = u.getRole() != null ? u.getRole().getNombre() : "ESTUDIANTE";
        return org.springframework.security.core.userdetails.User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .roles(roleName)
                .disabled(!u.isActive())
                .build();
    }
}
