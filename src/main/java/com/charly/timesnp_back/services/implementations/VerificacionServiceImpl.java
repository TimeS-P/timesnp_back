package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.Verificacion;
import com.charly.timesnp_back.repositories.VerificacionRepository;
import com.charly.timesnp_back.services.VerificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VerificacionServiceImpl implements VerificacionService {

    private final VerificacionRepository verificacionRepository;

    /**
     * @return
     */
    @Override
    public List<Verificacion> obtenerVerificacionesPendientes() {
        // Obtenemos todas las verificaciones pendientes
        List<Verificacion> verificacionesPendientes = verificacionRepository.findAll()
                .stream()
                .filter(verificacion -> !verificacion.isVerificado())
                .toList();

        // Retornamos la lista de verificaciones pendientes
        return verificacionesPendientes;
    }
}
