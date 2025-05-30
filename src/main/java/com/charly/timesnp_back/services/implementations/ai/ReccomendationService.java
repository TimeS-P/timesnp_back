package com.charly.timesnp_back.services.implementations.ai;

import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.repositories.ContratacionRepository;
import com.charly.timesnp_back.repositories.ServicioGeneralRepository;
import com.google.cloud.aiplatform.v1.FindNeighborsRequest;
import com.google.cloud.aiplatform.v1.FindNeighborsResponse;
import com.google.cloud.aiplatform.v1.IndexDatapoint;
import com.google.cloud.aiplatform.v1.IndexEndpointName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReccomendationService {

    private final ContratacionRepository contratacionRepository;
    private final EmbeddingGeneratorService embeddingGeneratorService;
    private final VectorSearchService vectorSearchService;
    private final ServicioGeneralRepository servicioGeneralRepository;
    private final RestEmbeddingService restEmbeddingService;

    public List<ServicioGeneral> reccomendForProfile(UUID perfilId, int topK) {

        // 1) Obtén el historial de contrataciones
        // 1) Historial y categorías únicas
        List<Contratacion> contratos = contratacionRepository.findByPerfilId(perfilId);

        if (contratos.isEmpty()) {
            log.warn("No contracts found for profile ID: {}", perfilId);
            return new ArrayList<>();
        }

        Set<String> categorias = contratos.stream()
                .map(Contratacion::getServicioGeneral)
                .filter(s -> s != null && s.getProveedorHasServicio() != null && s.getProveedorHasServicio().getCategoriaServicio() != null)
                .map(s -> s.getProveedorHasServicio().getCategoriaServicio().getNombre())
                .collect(Collectors.toSet());

        // 2) Embedding del perfil
        String combined = contratos.stream()
                .map(c -> c.getServicioGeneral().getNombre() + " "
                        + c.getServicioGeneral().getDescripcion())
                .collect(Collectors.joining(". "));

        // 3) Genera el embedding del perfil
        List<Float> perfilVec = restEmbeddingService.generateEmbedding(combined);

        // 3) Construir las restricciones de categorías
        List<IndexDatapoint.Restriction> restrs = categorias.stream()
                .map(cat -> IndexDatapoint.Restriction.newBuilder()
                        .setNamespace("categoria")
                        .addAllowList(cat)
                        .build())
                .toList();


        // 4) Lanzar la query con filtros
        FindNeighborsResponse resp = vectorSearchService.searchWithRestrictions(perfilVec, topK, restrs);

        // 5) Mapeo de IDs y recuperación de entidades
        List<UUID> ids = resp.getNearestNeighborsList().stream()
                .flatMap(qn -> qn.getNeighborsList().stream())
                .map(n -> UUID.fromString(n.getDatapoint().getDatapointId()))
                .toList();

        // 6) Recupera y devuelve las entidades completas
        return servicioGeneralRepository.findAllById(ids);
    }


}
