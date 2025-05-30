package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.services.implementations.ContratacionService;
import com.charly.timesnp_back.services.implementations.ai.IndexingService;
import com.charly.timesnp_back.services.implementations.ai.ReccomendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
@Slf4j
/**
 * Controller for managing recommendations.
 * This controller provides endpoints to get and update recommendations for a user profile.
 */
public class ReccomendationController {

    private final ReccomendationService recService;
    private final ContratacionService contratacionService;
    private final IndexingService indexingService;

    @GetMapping
    public ResponseEntity<ApiResponseTemplate<List<ServicioGeneral>>> getRecommendations(
            @RequestParam(defaultValue = "5") int topK
    ) {
        // Get the profile ID from the security context
        Usuario loggedUser = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID profileId = loggedUser.getPerfil().getId();

        List<ServicioGeneral> recommendations = recService.reccomendForProfile(profileId, topK);
        return ResponseEntity.ok(new ApiResponseTemplate<>(true, "Recommendations retrieved successfully", recommendations));
    }

    /**
     * Api to update the recommendations for a profile
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponseTemplate<String>> updateRecommendations(
    ) {
        try {

            // Get the profile ID from the security context
            Usuario loggedUser = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UUID profileId = loggedUser.getPerfil().getId();

            List< Contratacion> contrataciones = contratacionService.getContrataciones(profileId);

            if (contrataciones.isEmpty()) {
                return ResponseEntity.ok(new ApiResponseTemplate<>(false, "No contracts found for the profile", null));
            }


            contrataciones.stream()
                    .map(Contratacion::getServicioGeneral)
                    .filter(servicioGeneral ->
                                    servicioGeneral != null &&
                                    servicioGeneral.getProveedorHasServicio() != null
                                            && servicioGeneral.getProveedorHasServicio().getCategoriaServicio() != null
                    ).forEach(servicioGeneral -> {
                        log.info("Indexing service: {}" , servicioGeneral.getId());
                        indexingService.indexServicio(servicioGeneral);
                    });


            return ResponseEntity.ok(new ApiResponseTemplate<>(true, "Recommendations updated successfully", null));
        } catch (Exception e) {
            log.error("Error while updating recommendations: {}", e.getMessage());
            return ResponseEntity
                    .status(500)
                    .body(new ApiResponseTemplate<>(false, "Error while updating recommendations: " + e.getMessage(), null));
        }
    }

}
