package es.daw.dashboard.repository;

import es.daw.dashboard.entity.ServidorMV;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServidorMvRepository extends JpaRepository<ServidorMV, Long> {
    long countByEstado(ServidorMV.EstadoServidor estado);
    Page<ServidorMV> findByEstado(ServidorMV.EstadoServidor estado, Pageable pageable);
    Page<ServidorMV> findByTipo(ServidorMV.TipoServidor tipo, Pageable pageable);
    Optional<ServidorMV> findByNombre(String nombre);


    long countByTipoAndEstado(ServidorMV.TipoServidor tipo, ServidorMV.EstadoServidor estado);

}
