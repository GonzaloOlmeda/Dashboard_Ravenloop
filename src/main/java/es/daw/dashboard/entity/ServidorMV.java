package es.daw.dashboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "servidor_mv")
@Data
public class ServidorMV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoServidor tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoServidor estado = EstadoServidor.OFFLINE;

    @Column(name = "memoria_total")
    private Integer memoriaTotal;

    @Column(name = "capacidad_disco")
    private Integer capacidadDisco;


    @ManyToMany(mappedBy = "servidores")
    @JsonIgnore
    private Set<Usuario> usuarios = new HashSet<>();

    public enum TipoServidor {
        SERVIDOR, MV
    }

    public enum EstadoServidor {
        ONLINE, OFFLINE
    }

}
