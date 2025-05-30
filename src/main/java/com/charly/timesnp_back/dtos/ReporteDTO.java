package com.charly.timesnp_back.dtos;

import java.util.UUID;

public record ReporteDTO (
        String comentario,
        UUID idContratacion,
        UUID idPerfil
){
}
