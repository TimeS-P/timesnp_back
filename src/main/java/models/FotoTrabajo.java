package models;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "foto_trabajo")
@Table(name = "foto_trabajo")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FotoTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "url_foto", nullable = true)
    private String url_foto;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_servicio_general", nullable = false)
    private ServicioGeneral servicioGeneral;


    public FotoTrabajo(String url_foto, ServicioGeneral servicioGeneral) {
        this.url_foto = url_foto;
        this.servicioGeneral = servicioGeneral;
    }

}
