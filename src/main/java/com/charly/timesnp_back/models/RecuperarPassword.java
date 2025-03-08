package com.charly.timesnp_back.models;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "RecuperarPassword")
@Table(name = "RecuperarPassword")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecuperarPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "expires", nullable = true)
    private LocalDateTime expires;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public RecuperarPassword(String token, LocalDateTime expires, Usuario usuario) {
        this.token = token;
        this.expires = expires;
        this.usuario = usuario;
    }
}
