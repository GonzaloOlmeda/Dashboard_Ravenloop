package es.daw.dashboard.dto.bd;

import lombok.Data;

@Data
public class IntegracionDTO {

    private Long id;
    private String nombreSistema;
    private String tipo;              // "identidades", "infraestructura", "monitorizacion"
    private String endpointBase;
    private String token;             // Ocultar en frontend si no es ADMIN
    private Integer frecuenciaSincronizacion;
}
