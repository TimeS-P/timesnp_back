package com.charly.timesnp_back.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ICloudinaryService {

    /**
     * Sube una imagen a Cloudinary
     * @param images imagen en base64
     * @return URL de la imagen subida
     * @throws Exception si ocurre un error al subir la imagen
     */
    public Map<String, String> uploadImages(MultipartFile[] images) throws Exception;

    /**
     * Elimina una imagen de Cloudinary
     * @param ids IDs de las imágenes a eliminar
     * @return mensaje de confirmación
     * @throws Exception si ocurre un error al eliminar la imagen
     */
    public ApiResponse deleteImage(List<String> ids) throws Exception;

}
