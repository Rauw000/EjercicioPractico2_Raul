package com.ejercicioPractico2.plataformaAcademica.Service;

import com.ejercicioPractico2.plataformaAcademica.Domain.Role;
import com.ejercicioPractico2.plataformaAcademica.Domain.User;
import com.ejercicioPractico2.plataformaAcademica.Repository.RoleRepository;
import com.ejercicioPractico2.plataformaAcademica.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public UserService(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User save(User u) {
        return userRepo.save(u);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    // queries
    public List<User> findByRoleNombre(String roleNombre) {
        return userRepo.findByRole_Nombre(roleNombre);
    }

    public List<User> findByCreatedBetween(LocalDateTime start, LocalDateTime end) {
        return userRepo.findByCreatedAtBetween(start, end);
    }

    public List<User> searchByNombreOrEmail(String q) {
        return userRepo.findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(q, q);
    }

    public long countActive() {
        return userRepo.countByActiveTrue();
    }

    public long countInactive() {
        return userRepo.countByActiveFalse();
    }

    public List<User> findAllOrderByCreatedAtDesc() {
        return userRepo.findAllByOrderByCreatedAtDesc();
    }
}
