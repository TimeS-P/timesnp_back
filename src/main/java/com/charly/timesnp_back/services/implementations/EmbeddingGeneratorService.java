package com.charly.timesnp_back.services.implementations;

import com.google.cloud.aiplatform.v1.PredictRequest;
import com.google.cloud.aiplatform.v1.PredictResponse;
import com.google.cloud.aiplatform.v1.PredictionServiceClient;
import com.google.protobuf.ListValue;
import com.google.protobuf.Struct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbeddingGeneratorService {

    @Value("${GCP_PROJECT_ID}")
    private String projectId;

    @Value("${GCP_LOCATION}")
    private String location;

    @Value("${VERTEX_MODEL_ID}")
    private String modelId;

    private final PredictionServiceClient predictionServiceClient;

    public List<Float> generateEmbedding(String text) {

        // Construir el nombre completo del modelo
        String modelFullId = String.format("projects/%s/locations/%s/publishers/google/models/%s", projectId, location, modelId);

        // Crear el valor para el texto
        com.google.protobuf.Value textValue = com.google.protobuf.Value.newBuilder().setStringValue(text).build();

        // Crear la instancia para la predicción
        Struct instance = Struct.newBuilder().putFields("content", textValue).build();

        // Construir la solicitud de predicción
        PredictRequest predictRequest = PredictRequest.newBuilder()
                .setEndpoint(modelFullId)
                .addInstances(com.google.protobuf.Value.newBuilder().setStructValue(instance).build())
                .build();

        // Realizar la predicción
        PredictResponse response = predictionServiceClient.predict(predictRequest);

        // Extraer el vector de embedding del response
        List<com.google.protobuf.Value> predictions = response.getPredictionsList();
        if (predictions.isEmpty()) {
            throw new RuntimeException("No se recibió ninguna predicción del modelo.");
        }

        com.google.protobuf.Value prediction = predictions.get(0);
        List<Float> embedding = new ArrayList<>();

        // Verificar si el valor es de tipo ListValue
        if (prediction.hasListValue()) {
            ListValue listValue = prediction.getListValue();
            for (com.google.protobuf.Value v : listValue.getValuesList()) {
                embedding.add((float) v.getNumberValue());
            }
        } else {
            throw new RuntimeException("El formato de la predicción no es el esperado.");
        }

        return embedding;

    }


}
