package models;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "servicio_general")
@Table(name = "servicio_general")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ServicioGeneral {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre", nullable = true, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = true, length = 650)
    private String descripcion;

    @Column(name = "precio", nullable = true)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servicio", nullable = false, length = 20)
    private TipoServicio tipoServicio;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_proveedor_has_servicio", nullable = true)
    private ProveedorHasServicio proveedorHasServicio;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_combo", nullable = true)
    private Combo combo;

    @OneToMany(mappedBy = "servicioGeneral")
    private List<Contratacion> contrataciones;

    @OneToMany(mappedBy = "servicioGeneral")
    private List<FotoTrabajo> fotos;

    @OneToMany(mappedBy = "servicioGeneral")
    private List<Reporte> reportes;

    @OneToMany(mappedBy = "servicioGeneral")
    private List<Mensaje> calificaciones;

    public ServicioGeneral(String nombre, String descripcion, BigDecimal precio, TipoServicio tipoServicio, ProveedorHasServicio proveedorHasServicio, Combo combo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipoServicio = tipoServicio;
        this.proveedorHasServicio = proveedorHasServicio;
        this.combo = combo;
    }

}
