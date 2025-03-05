package com.charly.timesnp_back.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "rol")
@Getter
@Setter
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, name = "nombre", nullable = false, length = 100)
    private RolNombre nombre;


    public Rol(RolNombre rolNombre) {
        this.nombre = rolNombre;
    }
}
