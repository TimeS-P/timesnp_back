package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.RecuperarPassword;
import com.charly.timesnp_back.models.VerificarCorreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecuperarPasswordRepository extends JpaRepository<RecuperarPassword, UUID> {

}
