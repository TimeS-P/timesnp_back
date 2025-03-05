package models;

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

@Entity(name = "solicitud_combo")
@Table(name = "solicitud_combo")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudCombo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "cantidad", nullable = true, columnDefinition = "boolean default false")
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_combo", nullable = true)
    private Combo combo;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_proveedor", nullable = true)
    private Proveedor proveedor;

    public SolicitudCombo(Boolean status, Combo combo, Proveedor proveedor) {
        this.status = status;
        this.combo = combo;
        this.proveedor = proveedor;
    }

}
