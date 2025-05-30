package com.charly.timesnp_back.services.implementations.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RestEmbeddingService {

    @Value("${GCP_PROJECT_ID}")
    private String projectId;

    @Value("${VERTEX_MODEL_ID:text-embedding-005}")
    private String modelId;

    @Value("${VERTEX_LOCATION:us-central1}")
    private String location;

    private final RestTemplate restTemplate;

    public RestEmbeddingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Float> generateEmbedding(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("El texto no puede ser vacío.");
        }

        // Construye la URL REST
        String url = String.format(
                "https://%s-aiplatform.googleapis.com/v1/projects/%s/locations/%s"
                        + "/publishers/google/models/%s:predict",
                location, projectId, location, modelId
        );

        // Payload JSON
        Map<String,Object> payload = Map.of(
                "instances",
                List.of(Map.of(
                        "content", text,
                        "task_type", "RETRIEVAL_QUERY"
                ))
        );

        // La cabecera Authorization ya la agrega el interceptor
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> resp = restTemplate.postForEntity(url, request, Map.class);


        // Parseo de la respuesta
        @SuppressWarnings("unchecked")
        List<Map<String,Object>> predictions =
                (List<Map<String,Object>>) resp.getBody().get("predictions");

        @SuppressWarnings("unchecked")
        Map<String,Object> embeddingsMap =
                (Map<String,Object>) predictions.get(0).get("embeddings");

        @SuppressWarnings("unchecked")
        List<Number> values =
                (List<Number>) embeddingsMap.get("values");

        // Convertir a List<Float>
        return values.stream()
                .map(Number::floatValue)
                .toList();
    }
}
