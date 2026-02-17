package es.daw.dashboard.repository;

import es.daw.dashboard.entity.ServidorMV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServidorMvRepository extends JpaRepository<ServidorMV, Long> {
    long countByEstado(ServidorMV.EstadoServidor estado);

}
