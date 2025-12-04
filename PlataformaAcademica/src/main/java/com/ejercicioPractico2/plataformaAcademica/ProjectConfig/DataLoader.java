package com.ejercicioPractico2.plataformaAcademica.ProjectConfig;

import com.ejercicioPractico2.plataformaAcademica.Domain.Role;
import com.ejercicioPractico2.plataformaAcademica.Domain.User;
import com.ejercicioPractico2.plataformaAcademica.Repository.RoleRepository;
import com.ejercicioPractico2.plataformaAcademica.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            Role rAdmin = roleRepo.findByNombre("ADMIN").orElseGet(() -> roleRepo.save(new Role("ADMIN")));
            Role rProf = roleRepo.findByNombre("PROFESOR").orElseGet(() -> roleRepo.save(new Role("PROFESOR")));
            Role rEst = roleRepo.findByNombre("ESTUDIANTE").orElseGet(() -> roleRepo.save(new Role("ESTUDIANTE")));

            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setNombre("Super");
                admin.setApellido("Admin");
                admin.setEmail("admin@local");
                admin.setRole(rAdmin);
                userRepo.save(admin);
            }

            if (userRepo.findByUsername("usuario").isEmpty()) {
                User u = new User();
                u.setUsername("Pepe");
                u.setPassword(encoder.encode("pepe123"));
                u.setNombre("Pepe");
                u.setApellido("Guzman");
                u.setEmail("peep@local");
                u.setRole(rEst);
                userRepo.save(u);
            }

            if (userRepo.findByUsername("profesor").isEmpty()) {
                User p = new User();
                p.setUsername("profesor");
                p.setPassword(encoder.encode("profesor123"));
                p.setNombre("Pro");
                p.setApellido("Fesor");
                p.setEmail("profesor@local");
                p.setRole(rProf);
                userRepo.save(p);
            }
        };
    }
}
