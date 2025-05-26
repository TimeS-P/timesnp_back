package com.charly.timesnp_back.services.implementations.ai;


import com.google.cloud.aiplatform.v1.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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
    private final IndexEndpointServiceClient indexEndpointServiceClient;

    public FindNeighborsResponse search(List<Float> queryVector, int neighborCount) {
        // Nombre completo del IndexEndpoint
        String endpointName = IndexEndpointName.of(projectId, location, indexEndpointId).toString();
        log.info("Buscando {} vecinos en el endpoint: {}", neighborCount, endpointName);

        // Listar para verificar que el client gRPC ve tus endpoints
        ListIndexEndpointsRequest listReq = ListIndexEndpointsRequest.newBuilder()
                .setParent("projects/" + projectId + "/locations/" + location)
                .build();
        for (IndexEndpoint ep : indexEndpointServiceClient.listIndexEndpoints(listReq).iterateAll()) {
            log.info("  → Endpoint disponible: {}", ep.getName());
        }

        // Construcción de la petición vector search
        IndexDatapoint dp = IndexDatapoint.newBuilder()
                .setDatapointId("query-" + System.currentTimeMillis())
                .addAllFeatureVector(queryVector)
                .build();
        FindNeighborsRequest.Query q = FindNeighborsRequest.Query.newBuilder()
                .setDatapoint(dp)
                .setNeighborCount(neighborCount)
                .build();
        FindNeighborsRequest req = FindNeighborsRequest.newBuilder()
                .setIndexEndpoint(endpointName)
                .setDeployedIndexId(deployedIndexId)
                .addQueries(q)
                .build();

        return matchServiceClient.findNeighbors(req);
    }

    public FindNeighborsResponse searchWithRestrictions(
            List<Float> vec, int neighborCount,
            List<IndexDatapoint.Restriction> restrictions
    ) {

        String endpointName = IndexEndpointName.of(projectId, location, indexEndpointId).toString();

        IndexDatapoint dp = IndexDatapoint.newBuilder()
                .setDatapointId("q-" + System.currentTimeMillis())
                .addAllFeatureVector(vec)
                .addAllRestricts(restrictions)
                .build();

        FindNeighborsRequest.Query q = FindNeighborsRequest.Query.newBuilder()
                .setDatapoint(dp)
                .setNeighborCount(neighborCount)
                .build();

        FindNeighborsRequest req = FindNeighborsRequest.newBuilder()
                .setIndexEndpoint(endpointName)
                .setDeployedIndexId(deployedIndexId)
                .addQueries(q)
                .build();

        return matchServiceClient.findNeighbors(req);
    }
}