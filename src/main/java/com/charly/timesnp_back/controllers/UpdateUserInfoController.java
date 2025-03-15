package com.charly.timesnp_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charly.timesnp_back.dtos.PerfilDTO;
import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.services.IChangePerfilData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/updateUserInfo")
public class UpdateUserInfoController {

    @Autowired
    IChangePerfilData changePerfilData;

    @PostMapping
    public ResponseEntity<ApiResponseTemplate<Perfil>> postMethodName(@RequestBody PerfilDTO perfilDTO) {
        try {
            Perfil perfil = changePerfilData.changeData(perfilDTO);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Perfil actualizado correctamente", perfil));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al actualizar el perfil: " + e.getMessage()));
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponseTemplate<Perfil>> getMethodName() {
        try {
            Perfil perfil = changePerfilData.getPerfilData().orElse(null);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Perfil obtenido correctamente", perfil));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al obtener el perfil: " + e.getMessage()));
        }
    }
}
