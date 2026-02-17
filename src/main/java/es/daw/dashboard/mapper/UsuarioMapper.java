package es.daw.dashboard.mapper;

import es.daw.dashboard.dto.bd.UsuarioDTO;
import es.daw.dashboard.entity.Role;
import es.daw.dashboard.entity.ServidorMV;
import es.daw.dashboard.entity.Usuario;
import org.mapstruct.*;

import java.util.Optional;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {ServidorMapper.class})
public interface UsuarioMapper {

    // ENTITY → DTO
    @Mapping(source = "role.nombre", target = "role")
    @Mapping(source = "servidores", target = "servidores", qualifiedByName = "servidoresToOptional")
    UsuarioDTO toDTO(Usuario usuario);

    // DTO → ENTITY
    @Mapping(source = "role", target = "role", qualifiedByName = "stringToRole")
    @Mapping(source = "servidores", target = "servidores", qualifiedByName = "optionalToServidores")
    Usuario toEntity(UsuarioDTO dto);

    // Convertir Set<ServidorMV> → Optional<ServidorDTO>
    @Named("servidoresToOptional")
    default Optional<?> servidoresToOptional(Set<ServidorMV> servidores) {
        if (servidores == null || servidores.isEmpty()) return Optional.empty();
        return Optional.of(servidores.iterator().next());
    }

    // Convertir Optional<ServidorDTO> → Set<ServidorMV>
    @Named("optionalToServidores")
    default Set<ServidorMV> optionalToServidores(Optional<?> optional) {
        return Set.of();
    }

    // Convertir String → Role
    @Named("stringToRole")
    default Role stringToRole(String roleName) {
        if (roleName == null) return null;
        Role role = new Role();
        role.setNombre(Role.RoleName.valueOf(roleName));
        return role;
    }
}
