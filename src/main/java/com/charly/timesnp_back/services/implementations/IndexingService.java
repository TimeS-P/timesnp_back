package com.charly.timesnp_back.services.implementations;


import com.charly.timesnp_back.models.ServicioGeneral;
import com.google.cloud.aiplatform.v1.IndexDatapoint;
import com.google.cloud.aiplatform.v1.IndexName;
import com.google.cloud.aiplatform.v1.IndexServiceClient;
import com.google.cloud.aiplatform.v1.UpsertDatapointsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexingService {

    @Value("${GCP_PROJECT_ID}")
    private String projectId;
    @Value("${GCP_LOCATION}")
    private String location;
    @Value("${VERTEX_INDEX_ID}")
    private String indexId;
    @Value("${VERTEX_INDEX_ENDPOINT_ID}")
    private String indexEndpointId;

    private final IndexServiceClient indexServiceClient;
    private final EmbeddingGeneratorService embeddingGenerator;

    public void indexServicio(ServicioGeneral servicio) {
        //  Genera el embedding del servicio
        String text = servicio.getNombre() + " " + servicio.getDescripcion();
        List<Float> vector = embeddingGenerator.generateEmbedding(text);

        // Construyes tu lista de restricciones así:
        List<IndexDatapoint.Restriction> restrictions = List.of(
                IndexDatapoint.Restriction.newBuilder()
                        .setNamespace("categoria")
                        .addAllowList(servicio.getProveedorHasServicio().getCategoriaServicio().getNombre())
                        .build()
        );

        // Construye el datapoint
        IndexDatapoint dp = IndexDatapoint.newBuilder()
                .setDatapointId(servicio.getId().toString())
                .addAllFeatureVector(vector)   // Embedding
                .addAllRestricts(restrictions) // Restrictions
                .build();

        // Prepara el upsert request
        String indexName = IndexName.of(projectId, location, indexId).toString();
        UpsertDatapointsRequest req = UpsertDatapointsRequest.newBuilder()
                .setIndex(indexName)
                .addDatapoints(dp)
                .build();

        // Ejecuta el upsert para guardar el datapoint en el index
        indexServiceClient.upsertDatapoints(req);
    }
}