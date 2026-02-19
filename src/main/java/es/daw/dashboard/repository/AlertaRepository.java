package es.daw.dashboard.repository;

import es.daw.dashboard.entity.Alerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    Page<Alerta> findByCategoria(Alerta.CategoriaAlerta categoria, Pageable pageable);

    Page<Alerta> findByServidorMvId(Long servidorId, Pageable pageable);

    long countByCategoria(Alerta.CategoriaAlerta categoria);
}
