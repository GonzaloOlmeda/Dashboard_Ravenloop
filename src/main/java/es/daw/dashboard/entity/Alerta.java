package es.daw.dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerta")
@Data
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaAlerta categoria;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(nullable = false, length = 500)
    private String mensaje;

    private Integer origen;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_alerta")
    private LocalDateTime fechaAlerta = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_integracion")
    private Integracion integracion;

    @ManyToOne
    @JoinColumn(name = "id_servidor_mv")
    private ServidorMV servidorMv;

    public enum CategoriaAlerta {
        CRITICA, ADVERTENCIA, INFORMATIVA
    }
}
