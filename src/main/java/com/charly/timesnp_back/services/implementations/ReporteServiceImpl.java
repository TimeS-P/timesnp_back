package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.Reporte;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.ReporteRepository;
import com.charly.timesnp_back.repositories.ServicioGeneralRepository;
import com.charly.timesnp_back.services.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;
    private final ServicioGeneralRepository servicioGeneralRepository;

    /**
     * @param idServicioGeneral ID del servicio general al que se le crea el reporte.
     * @param comentario        Comentario del reporte.
     */
    @Override
    public void crearReporte(UUID idServicioGeneral, String comentario) throws Exception {

        Usuario loggedUser = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ServicioGeneral servicioGeneral = servicioGeneralRepository.findById(idServicioGeneral)
                .orElseThrow(() -> new IllegalArgumentException("Servicio general no encontrado con ID: " + idServicioGeneral));

        if (loggedUser == null) {
            throw new IllegalStateException("No user is logged in");
        }

        Reporte newReport = new Reporte(
                comentario,
                new java.sql.Date(new Date().getTime()),
                loggedUser.getPerfil(),
                servicioGeneral
        );

        reporteRepository.save(newReport);

    }

    /**
     * @param idServicioGeneral ID del servicio general del cual se obtienen los reportes.
     * @return
     */
    @Override
    public List<Reporte> obtenerReportesPorServicioGeneral(UUID idServicioGeneral) throws Exception {

        List<Reporte> reportes = reporteRepository.findByServicioGeneralId(idServicioGeneral);

        return reportes;
    }
}
