package models;

import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "servicio_general")
@Table(name = "servicio_general")
public class ServicioGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre", nullable = true, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = true, length = 650)
    private String descripcion;

    @Column(name = "precio", nullable = true)
    private Double precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servicio", nullable = false, length = 20)
    private TipoServicio tipoServicio;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_proveedor_has_servicio", nullable = false)
    private ProveedorHasServicio proveedorHasServicio;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_combo", nullable = true)
    private Combo combo;

    public ServicioGeneral() {
    }

    public ServicioGeneral(String nombre, String descripcion, Double precio, TipoServicio tipoServicio, ProveedorHasServicio proveedorHasServicio, Combo combo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipoServicio = tipoServicio;
        this.proveedorHasServicio = proveedorHasServicio;
        this.combo = combo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public ProveedorHasServicio getProveedorHasServicio() {
        return proveedorHasServicio;
    }

    public void setProveedorHasServicio(ProveedorHasServicio proveedorHasServicio) {
        this.proveedorHasServicio = proveedorHasServicio;
    }

    public Combo getCombo() {
        return combo;
    }

    public void setCombo(Combo combo) {
        this.combo = combo;
    }
}
