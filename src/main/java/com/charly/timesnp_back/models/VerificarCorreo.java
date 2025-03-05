package com.charly.timesnp_back.models;

import java.sql.Date;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "verificar_correo")
@Table(name = "verificar_correo")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class VerificarCorreo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "expires", nullable = true)
    private Date expires;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name="token", nullable = false, length = 300)
    private String token;

    public VerificarCorreo(Usuario usuario, String token) {
        this.usuario = usuario;
        this.token = token;
    }
}
