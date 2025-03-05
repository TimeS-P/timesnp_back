package com.charly.timesnp_back.models;

import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "dias_libres")
@Table(name = "dias_libres")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiasLibres {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia", nullable = true)
    private Dias dia;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_proveedor_has_servicio", nullable = false)
    private ProveedorHasServicio proveedorHasServicio;

    public DiasLibres(Dias dia, ProveedorHasServicio proveedorHasServicio) {
        this.dia = dia;
        this.proveedorHasServicio = proveedorHasServicio;
    }

}
