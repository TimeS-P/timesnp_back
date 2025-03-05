package com.charly.timesnp_back.models;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "domicilio")
@Table(name = "domicilio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Domicilio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "calle", nullable = true, length = 200)
    private String calle;

    @Column(name = "numero", nullable = true, length = 10)
    private String numero;

    @Column(name = "colonia", nullable = true, length = 100)
    private String colonia;

    @Column(name = "cp", nullable = true, length = 10)
    private String cp;

    @Column(name = "ciudad", nullable = true, length = 100)
    private String ciudad;

    @Column(name = "estado", nullable = true, length = 100)
    private String estado;

    @Column(name = "pais", nullable = true, length = 100)
    private String pais;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    public Domicilio(String calle, String numero, String colonia, String cp, String ciudad, String estado, String pais,
            Usuario usuario) {
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.cp = cp;
        this.ciudad = ciudad;
        this.estado = estado;
        this.pais = pais;
        this.usuario = usuario;
    }

}
