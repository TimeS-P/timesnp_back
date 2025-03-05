package models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "tipo_precio")
@Table(name = "tipo_precio")
public class TipoPrecio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre", nullable = true, length = 100)
    private String unidad_medida;

    @OneToMany(mappedBy = "tipoPrecio")
    private List<ProveedorHasServicio> proveedorHasServicios;

    public TipoPrecio() {
    }

    public TipoPrecio(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUnidadMedida() {
        return unidad_medida;
    }

    public void setUnidadMedida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

}
