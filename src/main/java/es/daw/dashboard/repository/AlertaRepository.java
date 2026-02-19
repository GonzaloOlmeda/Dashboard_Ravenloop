package es.daw.dashboard.repository;

import es.daw.dashboard.entity.Alerta;
import es.daw.dashboard.entity.ServidorMV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    @Query("SELECT a FROM Alerta a LEFT JOIN a.servidorMv s WHERE " +
           "(:categoria IS NULL OR a.categoria = :categoria) AND " +
           "(:tipoServidor IS NULL OR s.tipo = :tipoServidor) AND " +
           "(:fechaInicio IS NULL OR a.fechaAlerta >= :fechaInicio) AND " +
           "(:fechaFin IS NULL OR a.fechaAlerta <= :fechaFin) " +
           "ORDER BY a.fechaAlerta DESC")
    Page<Alerta> findByFilters(
            @Param("categoria") Alerta.CategoriaAlerta categoria,
            @Param("tipoServidor") ServidorMV.TipoServidor tipoServidor,
            @Param("fechaInicio") java.time.LocalDateTime fechaInicio,
            @Param("fechaFin") java.time.LocalDateTime fechaFin,
            Pageable pageable
    );

    @Query("SELECT COUNT(a) FROM Alerta a WHERE a.categoria = :categoria")
    long countByCategoria(@Param("categoria") Alerta.CategoriaAlerta categoria);

    // Métodos adicionales por si se necesitan
    Page<Alerta> findByCategoria(Alerta.CategoriaAlerta categoria, Pageable pageable);

    Page<Alerta> findByServidorMvId(Long servidorId, Pageable pageable);

    Page<Alerta> findByServidorMvTipo(ServidorMV.TipoServidor tipo, Pageable pageable);
}
