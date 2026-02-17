package es.daw.dashboard.dto.bd;

import lombok.Data;

@Data
public class ServidorDTO {

    private Long id;
    private String nombre;
    private String tipo;       // "SERVIDOR" o "MV"
    private String estado;     // "ONLINE" o "OFFLINE"
    private Integer memoriaTotal;
    private Integer capacidadDisco;

    // Métricas en tiempo real (obtenidas de API de monitorización)
    private Double cpuUsage;      // % de uso de CPU
    private Double memoriaUsada;  // % de memoria usada
    private Double discoUsado;    // % de disco usado
}
