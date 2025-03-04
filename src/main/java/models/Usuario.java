package models;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "usuario")
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "correo", nullable = true, length = 100, unique = true)
    private String correo;

    @Column(name = "password", nullable = true, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 100)
    private Rol rol;

    @OneToOne(mappedBy = "usuario")
    private VerificarCorreo verificarCorreo;

    @OneToMany(mappedBy = "usuario")
    private List<Domicilio> domicilios;

    @OneToOne(mappedBy = "usuario")
    private Perfil perfil;

    @OneToOne(mappedBy = "usuario")
    private RecuperarPassword recuperarPassword;

    public Usuario() {}

    public Usuario(String correo, String password, Rol rol) {
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public VerificarCorreo getVerificarCorreo() {
        return verificarCorreo;
    }

    public void setVerificarCorreo(VerificarCorreo verificarCorreo) {
        this.verificarCorreo = verificarCorreo;
    }
}
