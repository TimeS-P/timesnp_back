package com.charly.timesnp_back.dtos;

public record LoginResponseDTO (
        String status,
        String jwtToken
) {
}
