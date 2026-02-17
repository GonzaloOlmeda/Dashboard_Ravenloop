package es.daw.dashboard.dto.APIs;

import lombok.Data;

import java.util.List;

@Data
public class UsuariosExternosPageDTO {

    // Lista paginada
    private List<UsuarioExternoDTO> usuarios;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;

    // KPIs para las cards (usuarios totales por sistema)
    private Long totalOffice365;
    private Long totalGitLab;
    private Long totalJira;
}
