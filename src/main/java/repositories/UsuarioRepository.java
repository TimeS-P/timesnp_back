package repositories;

import models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {


    Optional<Usuario> findByEmail(String email);

    // Exists by email
    boolean existsByEmail(String email);

}
