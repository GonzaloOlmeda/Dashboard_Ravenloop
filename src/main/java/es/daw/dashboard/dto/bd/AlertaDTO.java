package es.daw.dashboard.dto.bd;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlertaDTO {

    private Long id;
    private String categoria;        // "CRITICA", "ADVERTENCIA", "INFORMATIVA"
    private String tipo;            // Tipo de alerta
    private String mensaje;         // Mensaje descriptivo
    private Boolean activo;         // Estado de la alerta
    private LocalDateTime fechaAlerta;  // Fecha y hora de creación

    // Sistema (Integración) relacionado
    private Long integracionId;
    private String integracionNombre;

    // Servidor relacionado (opcional - puede ser null)
    private Long servidorId;
    private String servidorNombre;
}
