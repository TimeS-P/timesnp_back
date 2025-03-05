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

@Entity(name = "resena")
@Table(name = "resena")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "calificacion", nullable = true)
    private int calificacion;

    @Column(name = "comentario", nullable = true)
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_contratacion", nullable = false)
    private Contratacion contratacion;

    public Resena(int calificacion, String comentario, Contratacion contratacion) {
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.contratacion = contratacion;
    }
    
}
