package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.models.Combo;
import com.charly.timesnp_back.models.Rol;
import com.charly.timesnp_back.services.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/combo")
public class CombosController {

    @Autowired
    private ComboService comboService;

    // Endpoint to get all combos
    @GetMapping("get-combos")
    public ResponseEntity<ApiResponseTemplate<List<Combo>>> getCombos() {
        try {
            List<Combo> combos = comboService.getCombos();
            return ResponseEntity.ok(ApiResponseTemplate.ok("Combos retrieved successfully", combos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error retrieving combos: " + e.getMessage()));
        }
    }

    @GetMapping("/get-roles")
    public ResponseEntity<ApiResponseTemplate<List<Rol>>> getRoles() {
        try {
            List<Rol> roles = comboService.getRoles();
            return ResponseEntity.ok(ApiResponseTemplate.ok("Roles retrieved successfully", roles));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error retrieving roles: " + e.getMessage()));
        }
    }
}
