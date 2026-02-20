package es.daw.dashboard.repository;

import es.daw.dashboard.entity.Alerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

     //Obtiene las alertas con o sin filtro
    @Query("SELECT a FROM Alerta a WHERE " +
           "(:categoria IS NULL OR a.categoria = :categoria) AND " +
           "(:fechaInicio IS NULL OR a.fechaAlerta >= :fechaInicio) AND " +
           "(:fechaFin IS NULL OR a.fechaAlerta <= :fechaFin) " +
           "ORDER BY a.fechaAlerta DESC")
    Page<Alerta> findByFilters(
            @Param("categoria") Alerta.CategoriaAlerta categoria,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            Pageable pageable
    );


// Obtiene la canidad de alertas segun la categoria
    @Query("SELECT COUNT(a) FROM Alerta a WHERE a.categoria = :categoria")
    long countByCategoria(@Param("categoria") Alerta.CategoriaAlerta categoria);


    Page<Alerta> findByCategoria(Alerta.CategoriaAlerta categoria, Pageable pageable);



    // Encontrar alertas inactivas con más de 30 días desde su creación
    @Query("SELECT a FROM Alerta a WHERE a.activo = false " +
           "AND a.fechaAlerta <= :fechaLimite")
    List<Alerta> findAlertasInactivasParaEliminar(@Param("fechaLimite") LocalDateTime fechaLimite);

    // Eliminar alertas inactivas con más de 30 días desde su creación
    @Modifying
    @Query("DELETE FROM Alerta a WHERE a.activo = false " +
           "AND a.fechaAlerta <= :fechaLimite")
    int deleteAlertasInactivas(@Param("fechaLimite") LocalDateTime fechaLimite);
}
