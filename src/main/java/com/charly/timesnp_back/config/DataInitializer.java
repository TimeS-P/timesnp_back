package com.charly.timesnp_back.config;

import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.Rol;
import com.charly.timesnp_back.models.RolNombre;
import com.charly.timesnp_back.repositories.ContratacionRepository;
import com.charly.timesnp_back.repositories.RolRepository;
import com.charly.timesnp_back.services.implementations.ai.IndexingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class DataInitializer {


    @Bean
    public CommandLineRunner loadData(RolRepository repository, IndexingService indexingService, ContratacionRepository contratacionRepository) {
        return (args) -> {
            if (repository.count() == 0) {
                repository.save(new Rol(RolNombre.ROLE_USUARIO));
                repository.save(new Rol(RolNombre.ROLE_ADMIN));
                repository.save(new Rol(RolNombre.ROLE_VERIFICADOR));
                repository.save(new Rol(RolNombre.ROLE_PROVEEDOR));
            }


            // Initialize the existing contracts indexing the services in gcp vertex ai
            // Get ServicesGeneral

//            List<Contratacion> contrataciones = contratacionRepository.findAll();
//
//            if (!contrataciones.isEmpty()) {
//                contrataciones.forEach(contratacion -> {
//                    if (contratacion.getServicioGeneral() != null) {
//                        System.out.println("Indexing service: " + contratacion.getServicioGeneral().getId());
//                        indexingService.indexServicio(contratacion.getServicioGeneral());
//                    }
//                });
//            }


        };
    }


}
