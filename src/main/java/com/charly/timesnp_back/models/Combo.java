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

@Entity(name = "combo")
@Table(name = "combo")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Combo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @OneToOne(mappedBy = "combo")
    private ServicioGeneral servicioGeneral;

    @OneToMany(mappedBy = "combo")
    private List<SolicitudCombo> solicitudCombos;

    @OneToOne(mappedBy = "combo")
    private ComboHasProveedor comboHasProveedor;

    public Combo(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

}
