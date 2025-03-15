package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.services.ICloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements ICloudinaryService {

    // Inyectamos la instancia de Cloudinary en el servicio por constructor
    private final Cloudinary cloudinary;

    /**
     * @param images imagen en base64
     * @return Map con el id y la URL de la imagen subida
     * @throws Exception
     */
    @Override
    public Map<String, String> uploadImages(MultipartFile[] images) throws Exception {

        Map<String, String> response = new HashMap<>();

        for (MultipartFile file : images) {

            // Subimos la imagen a Cloudinary
            Map params = ObjectUtils.asMap(
                    "user_filename", true,
                    "unique_filename", false,
                    "overwrite", true
            );

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            // Guardamos el public id y la URL de la imagen subida
            response.put(
                    (String) uploadResult.get("public_id"), (String) uploadResult.get("secure_url")
            );

        }


        return response;
    }

    /**
     * @param imageId ID de la imagen a eliminar
     * @return
     * @throws Exception
     */
    @Override
    public String deleteImage(String imageId) throws Exception {
        return "";
    }
}
