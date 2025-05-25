package com.charly.timesnp_back.config.ai;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.aiplatform.v1.IndexServiceClient;
import com.google.cloud.aiplatform.v1.MatchServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1.PredictionServiceSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@Slf4j
@Profile("!prod")
public class VertexAiConfig {

    @Value("${GOOGLE_VERTEX_AI}")
    private String credentialsPath;


    @Bean
    public PredictionServiceClient predictionServiceClient() {

        try {
            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));


            // Configure the Vertex Client for with credentials
            PredictionServiceSettings predictionServiceSettings = PredictionServiceSettings.newBuilder()
                    .setCredentialsProvider(() -> credentials).build();


            // Vertex AI Client
            return PredictionServiceClient.create(predictionServiceSettings);

        } catch (IOException e) {

            log.error("Error on the validation of google credentials for Vertex AI Service account: {}", e.getMessage());

        }

        return null;
    }

    @Bean
    public MatchServiceClient matchServiceClient() throws IOException {
        return MatchServiceClient.create();
    }

    @Bean
    public IndexServiceClient indexServiceClient() throws IOException {
        return IndexServiceClient.create();
    }

}
