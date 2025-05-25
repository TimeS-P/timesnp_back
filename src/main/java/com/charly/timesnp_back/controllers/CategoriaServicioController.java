package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.models.CategoriaServicio;
import com.charly.timesnp_back.services.ICategoriaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaServicioController {

    @Autowired
    private ICategoriaServicioService categoriaServicioService;

    @GetMapping("/obtenerCategoriasServicios")
    public ResponseEntity<ApiResponseTemplate<List<CategoriaServicio>>> obtenerCategoriasServicios() {
        try {
            List<CategoriaServicio> categorias = categoriaServicioService.obtenerCategoriasServicios();
            return ResponseEntity.ok(ApiResponseTemplate.ok("Categorias de servicios obtenidas correctamente", categorias));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al obtener las categorias de servicios: " + e.getMessage()));
        }
    }
}