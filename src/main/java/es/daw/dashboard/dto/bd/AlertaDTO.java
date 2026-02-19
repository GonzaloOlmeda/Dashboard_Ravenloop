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

    // Nombre del servidor relacionado (accedido a través de la relación con ServidorMV)
    private String servidorNombre;
}
