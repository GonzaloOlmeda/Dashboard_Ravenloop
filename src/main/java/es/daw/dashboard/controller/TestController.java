package es.daw.dashboard.controller;


import es.daw.dashboard.dto.APIs.*;
import es.daw.dashboard.dto.bd.*;
import es.daw.dashboard.dto.dashboard.DashboardSummaryDTO;
import es.daw.dashboard.entity.*;
import es.daw.dashboard.repository.*;
import es.daw.dashboard.service.MockApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final DataSource dataSource;
    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    private final IntegracionRepository integracionRepository;
    private final ServidorMvRepository servidorMvRepository;
    private final AlertaRepository alertaRepository;
    private final MockApiService mockApiService;

    // ============================================
    // TEST BÁSICOS
    // ============================================


    @GetMapping("/db-connection")
    public Map<String, Object> testDbConnection() {
        Map<String, Object> result = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            result.put("status", "CONECTADO");
            result.put("database", connection.getCatalog());
            result.put("url", connection.getMetaData().getURL());
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
        }
        return result;
    }

    // ============================================
    // TEST BD
    // ============================================

    @GetMapping("/db-data")
    public Map<String, Object> testDbData() {
        Map<String, Object> result = new HashMap<>();
        try {
            result.put("status", "OK");
            result.put("conteo", Map.of(
                    "roles", roleRepository.count(),
                    "usuarios", usuarioRepository.count(),
                    "integraciones", integracionRepository.count(),
                    "servidores", servidorMvRepository.count(),
                    "alertas", alertaRepository.count()
            ));
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
        }
        return result;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/integraciones")
    public ResponseEntity<List<Integracion>> getIntegraciones() {
        return ResponseEntity.ok(integracionRepository.findAll());
    }

    @GetMapping("/servidores")
    public ResponseEntity<List<ServidorMV>> getServidores() {
        return ResponseEntity.ok(servidorMvRepository.findAll());
    }

    @GetMapping("/alertas")
    public ResponseEntity<Map<String, Object>> getAlertas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fechaAlerta"));
        Page<Alerta> alertasPage = alertaRepository.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("alertas", alertasPage.getContent());
        response.put("currentPage", alertasPage.getNumber());
        response.put("totalPages", alertasPage.getTotalPages());
        response.put("totalItems", alertasPage.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/alertas/categoria/{categoria}")
    public ResponseEntity<Map<String, Object>> getAlertasPorCategoria(
            @PathVariable String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Alerta.CategoriaAlerta categoriaEnum = Alerta.CategoriaAlerta.valueOf(categoria.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "fechaAlerta"));

        Page<Alerta> alertasPage = alertaRepository.findByCategoria(categoriaEnum, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("categoria", categoria);
        response.put("alertas", alertasPage.getContent());
        response.put("currentPage", alertasPage.getNumber());
        response.put("totalPages", alertasPage.getTotalPages());
        response.put("totalItems", alertasPage.getTotalElements());

        return ResponseEntity.ok(response);
    }

    // ============================================
    // TEST APIs MOCK (simulan datos reales)
    // ============================================

    @GetMapping("/mock/office365")
    public ResponseEntity<Office365SummaryDTO> getMockOffice365() {
        return ResponseEntity.ok(mockApiService.getMockOffice365Summary());
    }

    @GetMapping("/mock/gitlab")
    public ResponseEntity<GitLabSummaryDTO> getMockGitLab() {
        return ResponseEntity.ok(mockApiService.getMockGitLabSummary());
    }

    @GetMapping("/mock/jira")
    public ResponseEntity<JiraSummaryDTO> getMockJira() {
        return ResponseEntity.ok(mockApiService.getMockJiraSummary());
    }

    @GetMapping("/mock/usuarios-externos")
    public ResponseEntity<UsuariosExternosPageDTO> getMockUsuariosExternos(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<UsuarioExternoDTO> todos = mockApiService.getMockUsuariosExternos();

        // Aplicar filtro de búsqueda si existe
        if (search != null && !search.isEmpty()) {
            String searchLower = search.toLowerCase();
            todos = todos.stream()
                    .filter(u -> u.getNombre().toLowerCase().contains(searchLower)
                            || u.getEmail().toLowerCase().contains(searchLower))
                    .collect(Collectors.toList());
        }

        // Paginación manual
        int start = page * size;
        int end = Math.min(start + size, todos.size());
        List<UsuarioExternoDTO> paginados = todos.subList(start, end);

        // Construir respuesta
        UsuariosExternosPageDTO response = new UsuariosExternosPageDTO();
        response.setUsuarios(paginados);
        response.setCurrentPage(page);
        response.setPageSize(size);
        response.setTotalItems(todos.size());
        response.setTotalPages((int) Math.ceil((double) todos.size() / size));
        response.setTotalOffice365(mockApiService.getMockOffice365Summary().getTotalUsers());
        response.setTotalGitLab(mockApiService.getMockGitLabSummary().getTotalUsers());
        response.setTotalJira(mockApiService.getMockJiraSummary().getTotalUsers());

        return ResponseEntity.ok(response);
    }

    // ============================================
    // TEST DASHBOARD COMPLETO (BD + Mock APIs)
    // ============================================

    @GetMapping("/mock/dashboard")
    public ResponseEntity<DashboardSummaryDTO> getMockDashboard() {
        DashboardSummaryDTO dashboard = new DashboardSummaryDTO();
        dashboard.setLastUpdate(LocalDateTime.now());

        // Datos de APIs (mock)
        Office365SummaryDTO o365 = mockApiService.getMockOffice365Summary();
        GitLabSummaryDTO gitlab = mockApiService.getMockGitLabSummary();
        JiraSummaryDTO jira = mockApiService.getMockJiraSummary();

        dashboard.setOffice365(o365);
        dashboard.setGitlab(gitlab);
        dashboard.setJira(jira);
        dashboard.setTotalUsuariosOffice365(o365.getTotalUsers());
        dashboard.setTotalUsuariosGitLab(gitlab.getTotalUsers());
        dashboard.setTotalUsuariosJira(jira.getTotalUsers());

        // Datos de infraestructura (de tu BD)
        dashboard.setServidoresOnline(
                servidorMvRepository.countByTipoAndEstado(
                        ServidorMV.TipoServidor.SERVIDOR, ServidorMV.EstadoServidor.ONLINE));
        dashboard.setServidoresOffline(
                servidorMvRepository.countByTipoAndEstado(
                        ServidorMV.TipoServidor.SERVIDOR, ServidorMV.EstadoServidor.OFFLINE));
        dashboard.setMaquinasVirtualesOnline(
                servidorMvRepository.countByTipoAndEstado(
                        ServidorMV.TipoServidor.MV, ServidorMV.EstadoServidor.ONLINE));
        dashboard.setMaquinasVirtualesOffline(
                servidorMvRepository.countByTipoAndEstado(
                        ServidorMV.TipoServidor.MV, ServidorMV.EstadoServidor.OFFLINE));

        // Datos de alertas (de tu BD)
        dashboard.setAlertasCriticas(
                alertaRepository.countByCategoria(Alerta.CategoriaAlerta.CRITICA));
        dashboard.setAlertasAdvertencia(
                alertaRepository.countByCategoria(Alerta.CategoriaAlerta.ADVERTENCIA));
        dashboard.setAlertasInformativas(
                alertaRepository.countByCategoria(Alerta.CategoriaAlerta.INFORMATIVA));

        return ResponseEntity.ok(dashboard);
    }
}
