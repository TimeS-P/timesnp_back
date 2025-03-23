package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.services.IGcpStorageService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class GcpStorageServiceImpl implements IGcpStorageService {

    private final Storage storage;

    @Value("${GCP_BUCKET_NAME}")
    private String bucketName;


    /**
     * @param fileName    archivo a subir
     * @param content     contenido del archivo
     * @param contentType tipo de contenido del archivo
     * @return
     * @throws Exception
     */
    @Override
    public void uploadFile(String fileName, byte[] content, String contentType) throws Exception {

        // Id del object en el bucket combinando el nombre del bucket y el nombre del archivo
        BlobId blobId = BlobId.of(bucketName, fileName);
        // Información del object y metadata
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();

        // Subimos el archivo al bucket
        storage.create(blobInfo, content);

    }

    /**
     * @param fileName archivo a eliminar
     * @throws Exception
     */
    @Override
    public void deleteFile(String fileName) throws Exception {
        // Se crea un BlobId combinando el nombre del bucket y el nombre del archivo
        BlobId blobId = BlobId.of(bucketName, fileName);

        // Se intenta eliminar el archivo usando el cliente 'storage'
        if (storage.delete(blobId)) {
            return;
        }

        // En caso de que no se pueda eliminar el archivo, se lanza una excepción
        throw new RuntimeException("El archivo no existe: " + fileName);
    }

    /**
     * @param fileName archivo a descargar
     * @return
     * @throws Exception
     */
    @Override
    public byte[] downloadFile(String fileName) throws Exception {

        // Descargamos el archivo del bucket
        Blob blob = storage.get(BlobId.of(bucketName, fileName));

        // Si el archivo retornamos su contenido
        if (blob != null) {
            return blob.getContent();
        }

        // En caso contrario lanzamos una excepción
        throw new RuntimeException("El archivo no existe: " + fileName);
    }

    /**
     * @param fileName archivo a generar la URL
     * @return URL firmada por tiempo limitado de acceso a un archivo
     * @throws Exception
     */
    @Override
    public URL generateSignedUrl(String fileName) throws Exception {

        // Creamos la información del archivo en base al nombre del bucket y el nombre del archivo
        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, fileName)).build();

        // Generamos al URL firmada con una duración de 15 minutos y con la opción de firmado v4
        URL signedUrl = storage.signUrl(blobInfo, 5, TimeUnit.MINUTES, Storage.SignUrlOption.withV4Signature());

        return signedUrl;
    }
}
