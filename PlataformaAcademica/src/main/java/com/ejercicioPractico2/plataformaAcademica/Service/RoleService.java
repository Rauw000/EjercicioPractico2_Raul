package com.ejercicioPractico2.plataformaAcademica.Service;

import com.ejercicioPractico2.plataformaAcademica.Domain.Role;
import com.ejercicioPractico2.plataformaAcademica.Repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepo;

    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    public List<Role> findAll() {
        return roleRepo.findAll();
    }

    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

    public Role save(Role role) {
        return roleRepo.save(role);
    }

    public void deleteById(Long id) {
        roleRepo.deleteById(id);
    }
}
