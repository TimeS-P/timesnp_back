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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "proveedor")
@Table(name = "proveedor")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre", nullable = true, length = 100)
    private String rfc;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @OneToMany(mappedBy = "proveedor")
    private List<ProveedorHasServicio> proveedorHasServicios;

    @OneToMany(mappedBy = "proveedor")
    private List<Combo> combos;

    @OneToMany(mappedBy = "proveedor")
    private List<SolicitudCombo> solicitudCombos;

    @OneToMany(mappedBy = "proveedor")
    private List<ComboHasProveedor> comboHasProveedores;

    public Proveedor(String rfc, Perfil perfil) {
        this.rfc = rfc;
        this.perfil = perfil;
    }

}
