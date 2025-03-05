package models;

import java.sql.Date;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "verificacion")
@Table(name = "verificacion")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Verificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "url_foto_credencial", nullable = true, length = 300)
    private String url_foto_credencial;

    @Column(name = "url_foto_perfil", nullable = true, length = 300)
    private Date fecha_verificacion;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    public Verificacion(String url_foto_credencial, Date fecha_verificacion, Perfil perfil) {
        this.url_foto_credencial = url_foto_credencial;
        this.fecha_verificacion = fecha_verificacion;
        this.perfil = perfil;
    }
}
