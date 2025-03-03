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

@Entity(name = "categoria_servicio")
@Table(name = "categoria_servicio")
public class CategoriaServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre", nullable = true, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = true, length = 650)
    private String descripcion;

    @Column(name = "icono", nullable = true, length = 300)
    private String icono;

    @OneToMany(mappedBy = "categoriaServicio" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProveedorHasServicio> proveedorHasServicios;

    public CategoriaServicio() {
    }

    public CategoriaServicio(String nombre, String descripcion, String icono) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

}
