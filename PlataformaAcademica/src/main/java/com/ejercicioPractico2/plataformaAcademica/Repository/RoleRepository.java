package com.ejercicioPractico2.plataformaAcademica.Repository;

import com.ejercicioPractico2.plataformaAcademica.Domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNombre(String nombre);
}
