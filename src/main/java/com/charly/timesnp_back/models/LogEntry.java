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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Instant timestamp;
    private String level;
    private String logger;
    @Column(length = 4000)
    private String message;

    @Column(name = "username")             // ya no es palabra reservada
    private String username;

    @Column(name = "ip")
    private String ip;

    @Column(name = "thread")
    private String thread;
}