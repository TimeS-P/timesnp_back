package com.charly.timesnp_back.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiResponseTemplate<T>(
        // Dupla de datos que se retornan en la respuesta
        @JsonProperty("OK")
        boolean OK,
        String message,
        T data
) {
    public static <T> ApiResponseTemplate<T> ok(String message, T data) {
        return new ApiResponseTemplate<>(true, message, data);
    }

    public static <T> ApiResponseTemplate<T> error(String message) {
        return new ApiResponseTemplate<>(false, message, null);
    }
}
