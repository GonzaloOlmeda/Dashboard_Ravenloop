package es.daw.dashboard.dto.bd;

import lombok.Data;

import java.util.List;

@Data
public class InfraestructuraSummaryDTO {

    // KPIs principales
    private Long totalServidores;
    private Long servidoresOnline;
    private Long servidoresOffline;

    private Long totalMaquinasVirtuales;
    private Long maquinasVirtualesOnline;
    private Long maquinasVirtualesOffline;

    // Listado
    private List<ServidorDTO> servidores;
}
