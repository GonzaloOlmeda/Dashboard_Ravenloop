package es.daw.dashboard.repository;

import es.daw.dashboard.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNombre(Role.RoleName nombre);

}
