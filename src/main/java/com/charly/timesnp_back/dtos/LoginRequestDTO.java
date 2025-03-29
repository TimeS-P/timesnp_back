package com.charly.timesnp_back.dtos;

public record LoginRequestDTO(
        String email,
        String password
) {
}
