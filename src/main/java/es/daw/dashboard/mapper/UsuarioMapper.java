package es.daw.dashboard.mapper;

import es.daw.dashboard.dto.bd.UsuarioDTO;
import es.daw.dashboard.dto.bd.ServidorDTO;
import es.daw.dashboard.entity.Role;
import es.daw.dashboard.entity.ServidorMV;
import es.daw.dashboard.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final ServidorMapper servidorMapper;

    // ENTITY → DTO
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setActivo(usuario.getActivo());

        // Role
        dto.setRole(usuario.getRole() != null ? usuario.getRole().getNombre().name() : null);

        // Servidores (solo uno, porque tu DTO usa Optional)
        if (usuario.getServidores() != null && !usuario.getServidores().isEmpty()) {
            ServidorMV servidor = usuario.getServidores().iterator().next();
            dto.setServidores(Optional.of(servidorMapper.toDTO(servidor)));
        } else {
            dto.setServidores(Optional.empty());
        }

        return dto;
    }

    // DTO → ENTITY
    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setActivo(dto.getActivo());

        // Role
        if (dto.getRole() != null) {
            Role role = new Role();
            role.setNombre(Role.RoleName.valueOf(dto.getRole()));
            usuario.setRole(role);
        }

        // Servidores
        // Servidores
        if (dto.getServidores() != null && dto.getServidores().isPresent()) {
            ServidorDTO servidorDTO = dto.getServidores().get();
            ServidorMV servidor = servidorMapper.toEntity(servidorDTO);
            usuario.setServidores(Set.of(servidor));
        } else {
            usuario.setServidores(Set.of());
        }


        return usuario;
    }
}
