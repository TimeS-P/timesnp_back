package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.services.IPerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
public class PerfilController {

    @Autowired
    IPerfilService perfilService;

    @GetMapping("/existsByCode")
    public ResponseEntity<ApiResponseTemplate<Perfil>> existsByCode(@RequestParam String codigoCompartir) {
        try{
            Perfil perfil = perfilService.existsByCodigoCompartir(codigoCompartir);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Perfil exists", perfil));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error checking perfil existence: " + e.getMessage()));
        }
    }

}
