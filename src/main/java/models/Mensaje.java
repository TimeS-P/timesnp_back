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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity(name = "mensaje")
@Table(name = "mensaje")
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "mensaje", nullable = true)
    private String mensaje;

    @Column(name = "fecha", nullable = true)
    private Date fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "id_emisor", nullable = true)
    private Perfil emisor;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "id_receptor", nullable = true)
    private Perfil remitente;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "id_servicio_general", nullable = true)
    private ServicioGeneral servicioGeneral;

    @PrePersist
    protected void onCreate() {
        this.fecha = Date.valueOf(LocalDate.now());
    }
    
	public Mensaje(String mensaje, Date fecha, Perfil emisor, Perfil remitente, ServicioGeneral servicioGeneral) {
		this.mensaje = mensaje;
		this.fecha = fecha;
		this.emisor = emisor;
		this.remitente = remitente;
		this.servicioGeneral = servicioGeneral;
	}

	public Mensaje() {
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Perfil getEmisor() {
		return emisor;
	}

	public void setEmisor(Perfil emisor) {
		this.emisor = emisor;
	}

	public Perfil getRemitente() {
		return remitente;
	}

	public void setRemitente(Perfil remitente) {
		this.remitente = remitente;
	}

	public ServicioGeneral getServicioGeneral() {
		return servicioGeneral;
	}

	public void setServicioGeneral(ServicioGeneral servicioGeneral) {
		this.servicioGeneral = servicioGeneral;
	}
    
}
