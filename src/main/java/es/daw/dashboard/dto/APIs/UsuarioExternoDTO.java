package es.daw.dashboard.dto.APIs;

import lombok.Data;

@Data
public class UsuarioExternoDTO {

    private String id;
    private String nombre;
    private String email;
    private String rol;
    private String sistema;    // "Office 365", "GitLab", "Jira"
    private Boolean activo;

}
