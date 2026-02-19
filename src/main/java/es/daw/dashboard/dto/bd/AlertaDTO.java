package es.daw.dashboard.dto.bd;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertaDTO {

    private Long id;
    private String categoria;    // "CRITICA", "ADVERTENCIA", "INFORMATIVA"
    private String tipo;
    private String mensaje;
    private String origen;
    private LocalDateTime fechaAlerta;
    private Boolean activo;

    // Info del servidor relacionado (si existe)
    private Long servidorId;
    private String servidorNombre;

    // Info de la integración relacionada
    private Long integracionId;
    private String integracionNombre;
}
