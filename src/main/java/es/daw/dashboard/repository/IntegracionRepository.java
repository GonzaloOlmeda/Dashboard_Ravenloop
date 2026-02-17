package es.daw.dashboard.repository;

import es.daw.dashboard.entity.Integracion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntegracionRepository extends JpaRepository<Integracion, Long> {

    Optional<Integracion> findByNombreSistema(String nombreSistema);
}
