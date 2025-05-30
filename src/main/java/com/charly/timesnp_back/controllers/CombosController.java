package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.dtos.ComboDTO;
import com.charly.timesnp_back.models.Combo;
import com.charly.timesnp_back.models.Rol;
import com.charly.timesnp_back.services.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/create-combo", consumes="multipart/form-data")
    public ResponseEntity<ApiResponseTemplate<String>> createCombo(@ModelAttribute ComboDTO comboDTO) {
        System.out.println("ComboDTO: " + comboDTO);
        try {
            Combo combo = comboService.createCombo(comboDTO);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Combo created successfully", "Combo creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error creating combo: " + e.getMessage()));
        }
    }

    @GetMapping("/get-combos-by-proveedor")
    public ResponseEntity<ApiResponseTemplate<List<Combo>>> getCombosByProveedor() {
        try {
            List<Combo> combos = comboService.getCombosByProveedor();
            return ResponseEntity.ok(ApiResponseTemplate.ok("Combos retrieved successfully", combos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error retrieving combos: " + e.getMessage()));
        }
    }

    @DeleteMapping("/delete-combo/{id}")
    public ResponseEntity<ApiResponseTemplate<String>> deleteCombo(@PathVariable String id) {
        try {
            comboService.deleteCombo(id);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Combo deleted successfully", "Combo eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error deleting combo: " + e.getMessage()));
        }
    }
}
