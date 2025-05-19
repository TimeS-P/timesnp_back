package com.charly.timesnp_back.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity(name = "mensaje")
@Table(name = "mensaje")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "mensaje", nullable = true)
    private String mensaje;

    @Column(name = "fecha", nullable = true)
    private Date fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_emisor", nullable = true)
    private Perfil emisor;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_chat", nullable = true)
    @JsonBackReference
    private Chat chat;

    @PrePersist
    protected void onCreate() {
        this.fecha = Date.valueOf(LocalDate.now());
    }
    
	public Mensaje(String mensaje, Date fecha, Perfil emisor) {
		this.mensaje = mensaje;
		this.fecha = fecha;
		this.emisor = emisor;
	}

}
