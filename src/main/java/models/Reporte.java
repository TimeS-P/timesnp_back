package models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity(name = "reporte")
@Table(name = "reporte")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "calificacion", nullable = true)
    private String comentario;

    @Column(name = "fecha", nullable = true)
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_perfil", nullable = true)
    private Perfil perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_servicio_general", nullable = true)
    private ServicioGeneral servicioGeneral;

    @PrePersist
    protected void onCreate() {
        this.fecha = Date.valueOf(LocalDate.now());
    }

    public Reporte() {
    }

    public Reporte(String comentario, Date fecha, Perfil perfil, ServicioGeneral servicioGeneral) {
        this.comentario = comentario;
        this.fecha = fecha;
        this.perfil = perfil;
        this.servicioGeneral = servicioGeneral;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
