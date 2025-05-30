package com.charly.timesnp_back.config.ai;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.aiplatform.v1.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Configuration
@Slf4j
@Profile("!prod")
public class VertexAiConfig {

    @Value("${GOOGLE_VERTEX_AI}")
    private String credentialsPath;

    @Value("${GCP_LOCATION}")
    private String indexLocation;

    @Bean
    public PredictionServiceClient predictionServiceClient() {
        try {
            String location = "us-central1";
            // 1) Carga las credenciales desde tu JSON
            GoogleCredentials rawCreds = GoogleCredentials
                    .fromStream(new FileInputStream(credentialsPath));

            // 2) Añade el scope de Cloud Platform
            GoogleCredentials scopedCreds = rawCreds
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            // 3) Construye las settings con un FixedCredentialsProvider
            PredictionServiceSettings settings = PredictionServiceSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(scopedCreds))
                    .setEndpoint("us-central1-aiplatform.googleapis.com:443")
                    .build();

            return PredictionServiceClient.create(settings);

        } catch (IOException e) {
            log.error("Error creando PredictionServiceClient", e);
            throw new IllegalStateException(e);
        }
    }


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate rt = new RestTemplate();

        try {

            GoogleCredentials creds = GoogleCredentials.fromStream(new FileInputStream(credentialsPath))
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            // Interceptor para añadir automáticamente el Bearer token
            ClientHttpRequestInterceptor authInterceptor = (request, body, exec) -> {
                // refresca si hace falta
                synchronized (creds) {
                    if (creds.getAccessToken() == null || creds.getAccessToken().getExpirationTime().before(new java.util.Date())) {
                        creds.refresh();
                    }
                }
                String token = creds.getAccessToken().getTokenValue();
                request.getHeaders().setBearerAuth(token);
                return exec.execute(request, body);
            };

            rt.setInterceptors(Collections.singletonList(authInterceptor));
        } catch (IOException e) {
            log.error("No se pudieron cargar las credenciales de GCP", e);
            throw new IllegalStateException(e);
        }


        return rt;
    }

    /**
     * Cliente gRPC para búsquedas vectoriales (MatchService).
     * Apunta a "<location>-aiplatform.googleapis.com:443".
     */
    @Bean
    public MatchServiceClient matchServiceClient() {
        try {

            GoogleCredentials creds = GoogleCredentials.fromStream(new FileInputStream(credentialsPath))
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));


            String publicEndpoint = "401103517.us-east1-627901673986.vdb.vertexai.goog";
            String endpoint = publicEndpoint + ":443";
            MatchServiceSettings settings = MatchServiceSettings.newBuilder()
                    .setEndpoint(endpoint)
                    .setCredentialsProvider(FixedCredentialsProvider.create(creds))
                    .build();
            return MatchServiceClient.create(settings);
        } catch (IOException e) {
            log.error("Error creando MatchServiceClient", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * Cliente gRPC para gestionar el índice (IndexService).
     * Apunta al mismo endpoint regional.
     */
    @Bean
    public IndexServiceClient indexServiceClient() {
        try {

            GoogleCredentials creds = GoogleCredentials.fromStream(new FileInputStream(credentialsPath))
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            String endpoint = String.format("%s-aiplatform.googleapis.com:443", indexLocation);
            IndexServiceSettings settings = IndexServiceSettings.newBuilder()
                    .setEndpoint(endpoint)
                    .setCredentialsProvider(FixedCredentialsProvider.create(creds))
                    .build();
            return IndexServiceClient.create(settings);
        } catch (IOException e) {
            log.error("Error creando IndexServiceClient", e);
            throw new IllegalStateException(e);
        }
    }

    /**
     * Cliente gRPC para listar y gestionar Index Endpoints.
     */
    @Bean
    public IndexEndpointServiceClient indexEndpointServiceClient() {

        try {
            GoogleCredentials creds = GoogleCredentials.fromStream(new FileInputStream(credentialsPath))
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            String endpoint = String.format("%s-aiplatform.googleapis.com:443", indexLocation);
            IndexEndpointServiceSettings settings = IndexEndpointServiceSettings.newBuilder()
                    .setEndpoint(endpoint)
                    .setCredentialsProvider(FixedCredentialsProvider.create(creds))
                    .build();

            return IndexEndpointServiceClient.create(settings);
        } catch (IOException e) {
            log.error("Error creando IndexEndpointServiceClient", e);
            throw new IllegalStateException(e);
        }
    }

}
