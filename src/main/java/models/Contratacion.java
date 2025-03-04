package models;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "contratacion")
@Table(name = "contratacion")
public class Contratacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "fecha_inicio", nullable = true)
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = true)
    private Date fechaFin;

    @Column(name = "total", nullable = true)
    private BigDecimal total;

    @Column(name = "cantidad", nullable = true)
    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Column(name = "id_perfil", nullable = true)
    private Perfil perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Column(name = "id_servicio_general", nullable = true)
    private ServicioGeneral servicioGeneral;

    @OneToMany(mappedBy = "contratacion")
    private List<Resena> resenas;

    public Contratacion() {
    }

    public Contratacion(Date fechaInicio, Date fechaFin, BigDecimal total, int cantidad, Perfil perfil,
            ServicioGeneral servicioGeneral) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
        this.cantidad = cantidad;
        this.perfil = perfil;
        this.servicioGeneral = servicioGeneral;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public ServicioGeneral getServicioGeneral() {
        return servicioGeneral;
    }

    public void setServicioGeneral(ServicioGeneral servicioGeneral) {
        this.servicioGeneral = servicioGeneral;
    }
    

}
