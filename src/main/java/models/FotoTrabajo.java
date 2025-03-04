package models;

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
import jakarta.persistence.Table;

@Entity(name = "foto_trabajo")
@Table(name = "foto_trabajo")
public class FotoTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "url_foto", nullable = true)
    private String url_foto;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "id_servicio_general", nullable = false)
    private ServicioGeneral servicioGeneral;

    public FotoTrabajo() {
    }

    public FotoTrabajo(String url_foto, ServicioGeneral servicioGeneral) {
        this.url_foto = url_foto;
        this.servicioGeneral = servicioGeneral;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public ServicioGeneral getServicioGeneral() {
        return servicioGeneral;
    }

    public void setServicioGeneral(ServicioGeneral servicioGeneral) {
        this.servicioGeneral = servicioGeneral;
    }

}
