package com.charly.timesnp_back.services;

import com.cloudinary.Cloudinary;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ICloudinaryService {

    /**
     * Sube una imagen a Cloudinary
     * @param image imagen en base64
     * @return URL de la imagen subida
     * @throws Exception si ocurre un error al subir la imagen
     */
    public Map<String, String> uploadImages(MultipartFile[] images) throws Exception;

    /**
     * Elimina una imagen de Cloudinary
     * @param imageId ID de la imagen a eliminar
     * @return mensaje de confirmación
     * @throws Exception si ocurre un error al eliminar la imagen
     */
    public String deleteImage(String imageId) throws Exception;

}
