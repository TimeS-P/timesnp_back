package com.charly.timesnp_back.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "contratacion")
@Table(name = "contratacion")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Contratacion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "fecha_inicio", nullable = true)
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = true)
    private Date fechaFin;

    @Column(name = "total", nullable = true)
    private BigDecimal total;

    @Column(name = "cantidad", nullable = true)
    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_perfil", nullable = true)
    private Perfil perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_servicio_general", nullable = true)
    private ServicioGeneral servicioGeneral;

    @OneToMany(mappedBy = "contratacion")
    private List<Resena> resenas;

    public Contratacion(Date fechaInicio, Date fechaFin, BigDecimal total, int cantidad, Perfil perfil,
            ServicioGeneral servicioGeneral) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
        this.cantidad = cantidad;
        this.perfil = perfil;
        this.servicioGeneral = servicioGeneral;
    }

}
