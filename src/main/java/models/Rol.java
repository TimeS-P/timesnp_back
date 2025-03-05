package models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, name = "nombre", nullable = false, length = 100)
    private RolNombre nombre;

}
