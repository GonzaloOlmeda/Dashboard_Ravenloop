package es.daw.dashboard.dto.bd;

import lombok.Data;

import java.util.Optional;

@Data
public class UsuarioDTO {

    private Long id;
    private String nombre;
    private String email;
    private Boolean activo;
    private String role;                     // "ADMIN" o "USER"
    private Optional<ServidorDTO> servidores;    // Servidores asignados
}
