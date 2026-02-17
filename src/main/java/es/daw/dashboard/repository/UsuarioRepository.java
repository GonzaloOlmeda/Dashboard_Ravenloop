package es.daw.dashboard.repository;

import es.daw.dashboard.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findByEmail(String email, Pageable pageable);
}
