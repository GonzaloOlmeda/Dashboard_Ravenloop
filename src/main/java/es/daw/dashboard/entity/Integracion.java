package es.daw.dashboard.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "integracion")
@Data
public class Integracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_sistema", nullable = false, length = 100)
    private String nombreSistema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoIntegracion tipo;

    @Column(length = 500)
    private String token;

    @Column(name = "frecuencia_sincronizacion")
    private Integer frecuenciaSincronizacion;

    @Column(name = "endpoint_base", nullable = false, length = 500)
    private String endpointBase;

    public enum TipoIntegracion {
        identidades, infraestructura, monitorizacion
    }
}
