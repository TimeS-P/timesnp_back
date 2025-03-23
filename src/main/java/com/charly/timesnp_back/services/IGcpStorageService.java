package com.charly.timesnp_back.services;

import java.net.URL;

public interface IGcpStorageService {

    /**
     * Sube un archivo a Google Cloud Storage
     * @param fileName archivo a subir
     * @param content contenido del archivo
     * @param contentType tipo de contenido del archivo
     * @throws Exception si ocurre un error al subir el archivo
     */
    public void uploadFile(String fileName, byte[] content, String contentType) throws Exception;

    /**
     * Elimina un archivo de Google Cloud Storage
     * @param fileName archivo a eliminar
     * @throws Exception si ocurre un error al eliminar el archivo
     */
    public void deleteFile(String fileName) throws Exception;

    // Download file
    /**
     * Descarga un archivo de Google Cloud Storage
     * @param fileName archivo a descargar
     * @return contenido del archivo
     * @throws Exception si ocurre un error al descargar el archivo
     */
    public byte[] downloadFile(String fileName) throws Exception;

    /**
     * Genera una URL firmada para acceder a un archivo de Google Cloud Storage por un tiempo limitado
     * @param fileName archivo a generar la URL
     * @return URL firmada
     * @throws Exception si ocurre un error al generar la URL
     */
    public URL generateSignedUrl(String fileName) throws Exception;

}
