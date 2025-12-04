package com.ejercicioPractico2.plataformaAcademica.Repository;

import com.ejercicioPractico2.plataformaAcademica.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRole_Nombre(String roleNombre);

    List<User> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<User> findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(String nombrePart, String emailPart);

    long countByActiveTrue();

    long countByActiveFalse();

    List<User> findAllByOrderByCreatedAtDesc();
}
