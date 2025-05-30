package com.charly.timesnp_back.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageDTO {
    private String mensaje;
    private String emisor;
    private String fecha;
    private String chatId;
}
