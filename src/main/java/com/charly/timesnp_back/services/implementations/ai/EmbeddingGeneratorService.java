package com.charly.timesnp_back.services.implementations.ai;

import com.google.cloud.aiplatform.v1.PredictRequest;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ListValue;
import com.google.protobuf.NullValue;
import com.google.protobuf.Struct;
import com.google.protobuf.util.JsonFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmbeddingGeneratorService {

    @Value("${GCP_PROJECT_ID}")
    private String projectId;


    @Value("${VERTEX_MODEL_ID}")
    private String modelId;

    private final PredictionServiceClient predictionServiceClient;

    public List<Float> generateEmbedding(String text) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("El texto no puede ser vacío.");
        }

        String location = "us-central1";

        // 1) Resource name del modelo gestionado
        String endpoint = "projects/timesnpsynertech/locations/us-central1/publishers/google/models/text-embedding-005";

        // 2) Prepara el JSON de la instancia (content + task_type)
        String json = String.format(
                "{\"content\": \"%s\", \"task_type\": \"RETRIEVAL_QUERY\"}",
                text.replace("\"", "\\\"")
        );

        // 3) Mergea ese JSON en un Value.Builder
        com.google.protobuf.Value.Builder vb = com.google.protobuf.Value.newBuilder();
        try {
            JsonFormat.parser()
                    .ignoringUnknownFields()
                    .merge(json, vb);
        } catch (InvalidProtocolBufferException e) {
            log.error("Error al parsear el JSON: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        com.google.protobuf.Value instanceValue = vb.build();

        List<com.google.protobuf.Value> instances = List.of(instanceValue);

        // 4) Prepara un Value vacío para parameters (no lo usamos)
        com.google.protobuf.Value emptyParameters =
                com.google.protobuf.Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build();

        // 5) Llama a predict(endpoint, instances, parameters)
        PredictResponse response = predictionServiceClient.predict(endpoint, instances, emptyParameters);

        // 6) Procesa la respuesta
        if (response.getPredictionsCount() == 0) {
            throw new RuntimeException("No se recibió ninguna predicción.");
        }

        Struct s = response.getPredictions(0).getStructValue();
        List<com.google.protobuf.Value> vals =
                s.getFieldsOrThrow("embeddings")
                        .getListValue()
                        .getValuesList();

        List<Float> embedding = new ArrayList<>(vals.size());
        for (com.google.protobuf.Value v : vals) {
            embedding.add((float) v.getNumberValue());
        }
        return embedding;

    }


}
