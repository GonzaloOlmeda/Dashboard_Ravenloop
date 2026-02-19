package es.daw.dashboard.dto.bd;

import lombok.Data;

@Data
public class AlertaRequestDTO {

    private String categoria;    // "CRITICA", "ADVERTENCIA", "INFORMATIVA"
    private String tipo;         // Tipo de alerta (ej: "CPU_HIGH", "MEMORY_LOW")
    private String mensaje;      // Mensaje descriptivo de la alerta
    private Long integracionId;  // ID del sistema (integración) - obligatorio
    private String servidorNombre; // Nombre del servidor - opcional (se busca en la BD)
    private Boolean activo;      // Estado de la alerta (true/false) - opcional, por defecto true al crear
}
