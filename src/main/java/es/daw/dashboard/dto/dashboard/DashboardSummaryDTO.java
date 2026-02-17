package es.daw.dashboard.dto.dashboard;

import es.daw.dashboard.dto.APIs.GitLabSummaryDTO;
import es.daw.dashboard.dto.APIs.JiraSummaryDTO;
import es.daw.dashboard.dto.APIs.Office365SummaryDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DashboardSummaryDTO {

    private LocalDateTime lastUpdate;

    // KPIs de Identidades (de APIs externas)
    private Long totalUsuariosOffice365;
    private Long totalUsuariosGitLab;
    private Long totalUsuariosJira;

    // KPIs de Infraestructura (de tu BD)
    private Long servidoresOnline;
    private Long servidoresOffline;
    private Long maquinasVirtualesOnline;
    private Long maquinasVirtualesOffline;

    // KPIs de Alertas (en BD del dashboard )
    private Long alertasCriticas;
    private Long alertasAdvertencia;
    private Long alertasInformativas;

    // Detalle de identidades
    private Office365SummaryDTO office365;
    private GitLabSummaryDTO gitlab;
    private JiraSummaryDTO jira;
}
