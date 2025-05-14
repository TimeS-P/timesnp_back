package com.charly.timesnp_back.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "log_entry")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry {
    @Id
    @GeneratedValue
    private UUID id;

    private Instant timestamp;     // Fecha/hora de la acción
    private String level;          // INFO, ERROR, DEBUG…
    private String logger;         // Nombre del logger
    @Column(length = 4000)
    private String message;        // Mensaje del log
    private String user;           // Usuario (MDC)
    private String ip;             // IP (MDC)
    private String thread;         // Hilo de ejecución

    // getters/setters…
}