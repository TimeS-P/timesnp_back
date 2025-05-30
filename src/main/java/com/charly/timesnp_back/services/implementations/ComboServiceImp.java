package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.dtos.ComboDTO;
import com.charly.timesnp_back.models.*;
import com.charly.timesnp_back.repositories.*;
import com.charly.timesnp_back.services.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ComboServiceImp implements ComboService {

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private ComboHasProveedorRepository comboHasProveedorRepository;

    @Autowired
    private ServicioGeneralRepository servicioGeneralRepository;

    @Autowired
    private FotoRespository fotoRespository;

    @Autowired
    private CloudinaryServiceImpl cloudinaryService;


    @Override
    public List<Combo> getCombos() {
        List<Combo> combos = comboRepository.findAll();
        return combos;
    }

    @Override
    public List<Rol> getRoles() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;

            // Extraer los roles del usuario actual
            return userDetails.getAuthorities().stream()
                    .map(authority -> new Rol(RolNombre.valueOf(authority.getAuthority())))
                    .toList();
        }

        throw new IllegalStateException("No se pudo obtener el usuario autenticado.");
    }

    @Override
    public List<Combo> getCombosByProveedor() {
        String username = getCurrentUsername();
        if (username != null) {
            Perfil perfil = perfilRepository.findByUsuario_Email(username)
                    .orElseThrow(() -> new IllegalStateException("Perfil no encontrado para el usuario: " + username));
            Proveedor proveedor = perfil.getProveedor();
            if(proveedor == null){
                throw new IllegalStateException("Proveedor no encontrado para el perfil: " + perfil.getId());
            }
            return comboRepository.findByProveedor_Id(proveedor.getId()).orElse(List.of());
        } else {
            // Manejar el caso en que no se pudo obtener el nombre de usuario
            throw new IllegalStateException("No se pudo obtener el nombre de usuario.");
        }
    }

    @Override
    public Combo createCombo(ComboDTO comboDTO) {
        String username = getCurrentUsername();
        if (username != null) {
            Perfil perfil = perfilRepository.findByUsuario_Email(username)
                    .orElseThrow(() -> new IllegalStateException("Perfil no encontrado para el usuario: " + username));
            Proveedor proveedor = perfil.getProveedor();
            if(proveedor == null){
                throw new IllegalStateException("Proveedor no encontrado para el perfil: " + perfil.getId());
            }
            Combo combo = new Combo();
            combo.setProveedor(proveedor);

            comboRepository.save(combo);

            ComboHasProveedor comboHasProveedor = new ComboHasProveedor();
            comboHasProveedor.setCombo(combo);
            comboHasProveedor.setProveedor(proveedor);
            comboHasProveedorRepository.save(comboHasProveedor);

            ServicioGeneral servicioGeneral = new ServicioGeneral();
            servicioGeneral.setCombo(combo);
            servicioGeneral.setNombre(comboDTO.getNombre());
            servicioGeneral.setDescripcion(comboDTO.getDescripcion());
            String precioStr = comboDTO.getPrecio();
            System.out.println("Precio recibido: " + precioStr);
            if (precioStr == null || precioStr.isEmpty()) {
                throw new IllegalArgumentException("El precio no puede ser nulo o vacío");
            }

            BigDecimal precio;
            try {
                precio = new BigDecimal(precioStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El precio debe ser un número válido", e);
            }

            servicioGeneral.setPrecio(precio);
            servicioGeneral.setTipoServicio(TipoServicio.COMBO);
            servicioGeneralRepository.save(servicioGeneral);

            // Guardar la foto
            try{
                if(comboDTO.getImagen() != null){
                    //Crear un arreglo de imagenes con la imagen de comboDTO
                    MultipartFile[] imagenes = new MultipartFile[1];
                    imagenes[0] = comboDTO.getImagen();
                    Map<String, String> url = cloudinaryService.uploadImages(imagenes);
                    if (url != null) {
                        for(String key : url.keySet()){
                            FotoTrabajo fotoTrabajo = new FotoTrabajo();
                            fotoTrabajo.setUrl_foto(url.get(key));
                            fotoTrabajo.setServicioGeneral(servicioGeneral);
                            fotoTrabajo.setId_foto(key);
                            fotoRespository.save(fotoTrabajo);
                        }
                    } else {
                        throw new IllegalStateException("Error al subir la imagen a Cloudinary");
                    }

                }
            }catch (Exception e){
                throw new IllegalStateException("Error al subir la imagen a Cloudinary: " + e.getMessage());
            }
        }else{
            // Manejar el caso en que no se pudo obtener el nombre de usuario
            throw new IllegalStateException("No se pudo obtener el nombre de usuario.");
        }
        return null;
    }

    @Override
    public void deleteCombo(String id) {
        // Eliminar el combo
        try{
            //Verificar si el user es dueño del combo
            String username = getCurrentUsername();
            if (username == null) {
                throw new IllegalStateException("No se pudo obtener el nombre de usuario.");
            }
            Perfil perfil = perfilRepository.findByUsuario_Email(username)
                    .orElseThrow(() -> new IllegalStateException("Perfil no encontrado para el usuario: " + username));
            Proveedor proveedor = perfil.getProveedor();
            if(proveedor == null){
                throw new IllegalStateException("Proveedor no encontrado para el perfil: " + perfil.getId());
            }
            UUID uuid = UUID.fromString(id);
            Combo combo = comboRepository.findById(uuid).orElseThrow(() -> new IllegalStateException("Combo no encontrado con id: " + id));
            if (!combo.getProveedor().getId().equals(proveedor.getId())) {
                throw new IllegalStateException("No tienes permiso para eliminar este combo");
            }
            FotoTrabajo fotoTrabajo = fotoRespository.findByServicioGeneral_Id(combo.getServicioGeneral().getId()).orElse(null);
            if (fotoTrabajo != null) {
                try {
                    cloudinaryService.deleteImage(List.of(fotoTrabajo.getId_foto()));
                } catch (Exception e) {
                    throw new IllegalStateException("Error al eliminar la imagen de Cloudinary: " + e.getMessage());
                }
            }
            comboRepository.delete(combo);
        }catch (Exception e){
            throw new IllegalStateException("Error al eliminar el combo: " + e.getMessage());
        }
    }


    public String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }


}
