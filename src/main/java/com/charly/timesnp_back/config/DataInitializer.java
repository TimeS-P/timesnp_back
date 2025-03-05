package com.charly.timesnp_back.config;

import com.charly.timesnp_back.models.Rol;
import com.charly.timesnp_back.models.RolNombre;
import com.charly.timesnp_back.repositories.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {


    @Bean
    public CommandLineRunner loadData(RolRepository repository) {
        return (args) -> {
            if (repository.count() == 0) {
                repository.save(new Rol(RolNombre.ROLE_USUARIO));
                repository.save(new Rol(RolNombre.ROLE_ADMIN));
                repository.save(new Rol(RolNombre.ROLE_VERIFICADOR));
                repository.save(new Rol(RolNombre.ROLE_PROVEEDOR));
            }
        };
    }


}
