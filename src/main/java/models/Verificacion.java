package models;

import java.sql.Date;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "verificacion")
@Table(name = "verificacion")
public class Verificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "url_foto_credencial", nullable = true, length = 300)
    private String url_foto_credencial;

    @Column(name = "url_foto_perfil", nullable = true, length = 300)
    private Date fecha_verificacion;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    public Verificacion() {
    }

    public Verificacion(String url_foto_credencial, Date fecha_verificacion, Perfil perfil) {
        this.url_foto_credencial = url_foto_credencial;
        this.fecha_verificacion = fecha_verificacion;
        this.perfil = perfil;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl_foto_credencial() {
        return url_foto_credencial;
    }

    public void setUrl_foto_credencial(String url_foto_credencial) {
        this.url_foto_credencial = url_foto_credencial;
    }

    public Date getFecha_verificacion() {
        return fecha_verificacion;
    }

    public void setFecha_verificacion(Date fecha_verificacion) {
        this.fecha_verificacion = fecha_verificacion;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
