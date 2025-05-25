package com.charly.timesnp_back.services.implementations;


import com.google.cloud.aiplatform.v1.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VectorSearchService {

    @Value("${GCP_PROJECT_ID}")
    private String projectId;

    @Value("${GCP_LOCATION}")
    private String location;

    @Value("${VERTEX_INDEX_ENDPOINT_ID}")
    private String indexEndpointId;

    @Value("${VERTEX_DEPLOYED_INDEX_ID}")
    private String deployedIndexId;

    private final MatchServiceClient matchServiceClient;

    public FindNeighborsResponse search(List<Float> queryVector, int neighborCount) {
        String indexEndpointName = IndexEndpointName.of(projectId, location, indexEndpointId).toString();
        int numNeighbors = 5;

        // Construir el datapoint de consulta con el vector de floats
        IndexDatapoint queryDatapoint = IndexDatapoint.newBuilder()
                .setDatapointId("query-1")
                .addAllFeatureVector(queryVector)  // lista de floats del embedding
                .build();

        // Construir el objeto Query con el datapoint y número de vecinos deseados
        FindNeighborsRequest.Query query = FindNeighborsRequest.Query.newBuilder()
                .setDatapoint(queryDatapoint)
                .setNeighborCount(numNeighbors)
                .build();

        // Construir la petición FindNeighborsRequest
        FindNeighborsRequest request = FindNeighborsRequest.newBuilder()
                .setIndexEndpoint(indexEndpointName)
                .setDeployedIndexId(deployedIndexId)
                .addQueries(query)
                .setReturnFullDatapoint(false)
                .build();

        // Ejecutar la búsqueda vectorial usando MatchServiceClient
        return matchServiceClient.findNeighbors(request);
    }
}