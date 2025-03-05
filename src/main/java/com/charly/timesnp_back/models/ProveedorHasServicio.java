package com.charly.timesnp_back.models;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "proveedor_has_servicio")
@Table(name = "proveedor_has_servicio")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorHasServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "calificacion", nullable = true, columnDefinition = "int default 0")
    private int calificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_tipo_precio", nullable = false)
    private TipoPrecio tipoPrecio;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_categoria_servicio", nullable = false)
    private CategoriaServicio categoriaServicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @OneToOne(mappedBy = "proveedorHasServicio")
    private ServicioGeneral servicioGeneral;

    @OneToMany(mappedBy = "proveedorHasServicio")
    private List<DiasLibres> diasLibres;


    public ProveedorHasServicio(int calificacion, TipoPrecio tipoPrecio, CategoriaServicio categoriaServicio, Proveedor proveedor) {
        this.calificacion = calificacion;
        this.tipoPrecio = tipoPrecio;
        this.categoriaServicio = categoriaServicio;
        this.proveedor = proveedor;
    }

}
