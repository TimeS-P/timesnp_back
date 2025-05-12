package com.charly.timesnp_back.models;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity(name = "categoria_servicio")
@Table(name = "categoria_servicio")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre", nullable = true, length = 100)
    private String nombre;

    @Column(name = "icono", nullable = true, length = 300)
    private String icono;

    @OneToMany(mappedBy = "categoriaServicio")
    @JsonIgnore
    private List<ProveedorHasServicio> proveedorHasServicios;

    public CategoriaServicio(String nombre, String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }


}
