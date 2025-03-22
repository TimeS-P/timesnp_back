package com.charly.timesnp_back.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@ToString
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

    @Column(name = "fecha_nacimiento", nullable = true, length = 300)
    private Date fechaNacimiento;

    @Column(name = "genero", nullable = true, length = 255)
    private String genero;

    @Column(name = "descripcion", nullable = true, length = 100)
    private String descripcion;

    @Column(name = "foto", nullable = true, length = 300)
    private String foto;

    @Column(name = "puntos", nullable = true, columnDefinition = "int default 0")
    private int puntos;

    @Column(name = "codigo_compartir", nullable = true, length = 300)
    private String codigoCompartir;
    
    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToOne(mappedBy = "perfil")
    private Verificacion verificacion;

    @OneToOne(mappedBy = "perfil")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "perfil")
    private List<Contratacion> contrataciones;

    @OneToMany(mappedBy = "perfil")
    private List<Reporte> reportes;

    @OneToMany(mappedBy = "emisor")
    private List<Mensaje> mensajesReceptor;

    @OneToMany(mappedBy = "remitente")
    private List<Mensaje> mensajesEmisor;

    public Perfil(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, Date fechaNacimiento, String genero, String descripcion, String foto, Usuario usuario, int puntos, String codigoCompartir) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.descripcion = descripcion;
        this.foto = foto;
        this.usuario = usuario;
        this.puntos = puntos;
        this.codigoCompartir = codigoCompartir;
    }
}
