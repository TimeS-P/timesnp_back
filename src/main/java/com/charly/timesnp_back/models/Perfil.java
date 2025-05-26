package com.charly.timesnp_back.models;

import java.util.Date;
import java.util.List;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "perfil")
@Table(name = "perfil")
@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = {"contrataciones"})
@NoArgsConstructor
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre", nullable = true, length = 100)
    private String nombre;

    @Column(name = "apellido_paterno", nullable = true, length = 100)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", nullable = true, length = 100)
    private String apellidoMaterno;

    @Column(name = "telefono", nullable = true, length = 10)
    private String telefono;

    @Column(name = "foto", nullable = true, length = 300)
    private String foto;

    @Column(name = "puntos", nullable = true, columnDefinition = "int default 0")
    private int puntos;

    @Column(name = "codigo_compartir", nullable = true, length = 300)
    private String codigoCompartir;

    @Column(name = "fecha_nacimiento", nullable = true)
    private Date fechaNacimiento;

    @Column(name = "genero", nullable = true, length = 30)
    private String genero;

    @Column(name = "descripcion", nullable = true, length = 300)
    private String descripcion;
    
    @OneToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToOne(mappedBy = "perfil")
    private Verificacion verificacion;

    @OneToOne(mappedBy = "perfil")
    @JsonIgnore
    private Proveedor proveedor;

    @OneToMany(mappedBy = "perfil")
    @JsonIgnore
    @JsonBackReference
    private List<Contratacion> contrataciones;

    @OneToMany(mappedBy = "perfil")
    @JsonIgnore
    private List<Reporte> reportes;

    @OneToMany(mappedBy = "emisor")
    @JsonIgnore
    private List<Mensaje> mensajesReceptor;

    @OneToMany(mappedBy = "remitente")
    @JsonIgnore
    private List<Mensaje> mensajesEmisor;

    public Perfil(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String foto, Usuario usuario, int puntos, String codigoCompartir, String genero, String descripcion) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.foto = foto;
        this.usuario = usuario;
        this.puntos = puntos;
        this.codigoCompartir = codigoCompartir;
        this.genero = genero;
        this.descripcion = descripcion;
    }

    public Perfil(String nombre, Usuario usuario, String codigoCompartir) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.codigoCompartir = codigoCompartir;
    }

    public static String generateShareCode(String email) {
        // Obtener datos antes del arroba
        String emailName = email.split("@")[0];

        return emailName + "-" + System.currentTimeMillis();
    }
}
