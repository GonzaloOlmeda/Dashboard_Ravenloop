package es.daw.dashboard.dto.bd;

import lombok.Data;

import java.util.Optional;

@Data
public class AlertaPageDTO {

    private Optional<AlertaDTO> alertas;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    // KPIs de alertas (para cards superiores)
    private Long totalCriticas;
    private Long totalAdvertencias;
    private Long totalInformativas;
}
