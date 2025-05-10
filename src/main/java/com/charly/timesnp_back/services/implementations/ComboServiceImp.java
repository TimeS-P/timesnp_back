package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.Combo;
import com.charly.timesnp_back.models.Rol;
import com.charly.timesnp_back.models.RolNombre;
import com.charly.timesnp_back.repositories.ComboRepository;
import com.charly.timesnp_back.services.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComboServiceImp implements ComboService {

    @Autowired
    private ComboRepository comboRepository;

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
}
