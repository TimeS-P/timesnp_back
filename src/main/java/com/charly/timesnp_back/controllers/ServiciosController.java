package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.dtos.CrearServicioDTO;
import com.charly.timesnp_back.models.*;
import com.charly.timesnp_back.repositories.PerfilRepository;
import com.charly.timesnp_back.services.*;
import com.charly.timesnp_back.services.implementations.ComboServiceImp;
import com.charly.timesnp_back.services.implementations.ai.IndexingService;
import org.springframework.web.bind.annotation.*;

import com.charly.timesnp_back.dtos.ObtenerServiciosDTO;
import com.charly.timesnp_back.dtos.ServicioGeneralDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/servicios")
public class ServiciosController {

    @Autowired 
    IServicios servicios;

    @Autowired
    IProveedorHasServicio iProveedorHasServicio;

    @Autowired
    ICategoriaServicioService categoriaServicioService;

    @Autowired
    IProveedor proveedorService;

    @Autowired
    ITipoPrecio tipoPrecioService;

    @Autowired
    ComboServiceImp comboService;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    IndexingService indexingService;

    @GetMapping("/serviciosCategoria")
    public ResponseEntity<ApiResponseTemplate<List<ServicioGeneralDTO>>> getMethodName(@RequestParam UUID idCategoria, @RequestParam String filtro) {
        try {
            ObtenerServiciosDTO obtenerServiciosDTO = new ObtenerServiciosDTO(idCategoria, filtro);
            List<ServicioGeneral> serviciosList = servicios.obtenerServicios(obtenerServiciosDTO).orElse(null);
            List<ServicioGeneralDTO> serviciosDTOList = serviciosList.stream()
                    .map(ServicioGeneralDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponseTemplate.ok("Servicios obtenidos correctamente", serviciosDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al obtener los servicios: " + e.getMessage()));
        }
    }
    
    @GetMapping("/servicio")
    public ResponseEntity<ApiResponseTemplate<ServicioGeneralDTO>> getMethodName(@RequestParam UUID id) {
        try {
            ServicioGeneral servicio = servicios.obtenerServicioPorId(id);
            ServicioGeneralDTO servicioDTO = ServicioGeneralDTO.fromEntity(servicio);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Servicio obtenido correctamente", servicioDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al obtener el servicio: " + e.getMessage()));
        }
    }
    
    @PostMapping("/crearServicio")
    public ResponseEntity<ApiResponseTemplate<ServicioGeneralDTO>> crearServicio(@RequestBody CrearServicioDTO dto) {
        try {

            CategoriaServicio categoria = categoriaServicioService.obtenerCategoriaServicioPorId(dto.getIdCategoria());

            TipoPrecio tipoPrecio = tipoPrecioService.getTipoPrecioById(dto.getIdTipoPrecio());
            String username = comboService.getCurrentUsername();
            if (username == null) {
                return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Usuario no autenticado"));
            }
            Perfil perfil = perfilRepository.findByUsuario_Email(username)
                    .orElseThrow(() -> new IllegalStateException("Perfil no encontrado para el usuario: " + username));
            Proveedor proveedor = perfil.getProveedor();
            if(proveedor == null){
                throw new IllegalStateException("Proveedor no encontrado para el perfil: " + perfil.getId());
            }

            ProveedorHasServicio proveedorServicio = new ProveedorHasServicio();
            proveedorServicio.setCategoriaServicio(categoria);
            proveedorServicio.setProveedor(proveedor);
            proveedorServicio.setCalificacion(0);
            proveedorServicio.setTipoPrecio(tipoPrecio);
            UUID idProveedorHasServicio = iProveedorHasServicio.saveProveedorHasServicio(proveedorServicio);

            proveedorServicio = iProveedorHasServicio.getProveedorHasServicioById(idProveedorHasServicio);


            ServicioGeneral servicioGeneral = new ServicioGeneral();
            servicioGeneral.setPrecio(dto.getPrecio());
            servicioGeneral.setDescripcion(dto.getDescripcion());
            servicioGeneral.setNombre(dto.getNombre());
            servicioGeneral.setTipoServicio(TipoServicio.SERVICIO);
            servicioGeneral.setProveedorHasServicio(proveedorServicio);
            servicioGeneral.setCombo(null);

            servicios.crearServicio(servicioGeneral);
            // Indexar el servicio general
            indexingService.indexServicio(servicioGeneral);


            return ResponseEntity.ok(ApiResponseTemplate.ok("Servicio creado correctamente", ServicioGeneralDTO.fromEntity(servicioGeneral)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al crear el servicio: " + e.getMessage()));
        }
    }

}
