package es.daw.dashboard.service;

import es.daw.dashboard.dto.APIs.GitLabSummaryDTO;
import es.daw.dashboard.dto.APIs.JiraSummaryDTO;
import es.daw.dashboard.dto.APIs.Office365SummaryDTO;
import es.daw.dashboard.dto.APIs.UsuarioExternoDTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MockApiService {

    /**
     * Simula respuesta de Office 365 API
     */
    public Office365SummaryDTO getMockOffice365Summary() {
        Office365SummaryDTO dto = new Office365SummaryDTO();
        dto.setTotalUsers(160L);
        dto.setActiveUsersLast30Days(142L);
        dto.setLicensesAssigned(175L);
        return dto;
    }

    /**
     * Simula respuesta de GitLab API
     */
    public GitLabSummaryDTO getMockGitLabSummary() {
        GitLabSummaryDTO dto = new GitLabSummaryDTO();
        dto.setTotalUsers(161L);
        dto.setActiveUsers(140L);
        dto.setTotalProjects(87L);
        dto.setTotalGroups(12L);
        return dto;
    }

    /**
     * Simula respuesta de Jira API
     */
    public JiraSummaryDTO getMockJiraSummary() {
        JiraSummaryDTO dto = new JiraSummaryDTO();
        dto.setTotalUsers(16L);
        dto.setActiveUsers(14L);
        dto.setOpenIssues(43L);
        dto.setTotalProjects(5L);
        return dto;
    }

    /**
     * Simula lista de usuarios externos (O365 + GitLab + Jira)
     */
    public List<UsuarioExternoDTO> getMockUsuariosExternos() {
        List<UsuarioExternoDTO> usuarios = new ArrayList<>();

        // Usuarios Office 365
        usuarios.add(crearUsuarioExterno("1", "Ana Pérez", "ana@empresa.com", "Admin", "Office 365", true));
        usuarios.add(crearUsuarioExterno("2", "Juan Gomez", "juan@empresa.com", "IT", "Office 365", true));
        usuarios.add(crearUsuarioExterno("3", "Carlos Mendez", "carlos@empresa.com", "Developer", "Office 365", true));
        usuarios.add(crearUsuarioExterno("4", "Laura Sánchez", "laura@empresa.com", "Manager", "Office 365", false));

        // Usuarios GitLab
        usuarios.add(crearUsuarioExterno("5", "Pedro López", "pedro@empresa.com", "Developer", "GitLab", true));
        usuarios.add(crearUsuarioExterno("6", "Sara Martín", "sara@empresa.com", "DevOps", "GitLab", true));
        usuarios.add(crearUsuarioExterno("7", "Miguel Torres", "miguel@empresa.com", "Developer", "GitLab", true));

        // Usuarios Jira
        usuarios.add(crearUsuarioExterno("8", "Elena Ruiz", "elena@empresa.com", "Product Owner", "Jira", true));
        usuarios.add(crearUsuarioExterno("9", "David Flores", "david@empresa.com", "Tester", "Jira", true));

        return usuarios;
    }

    private UsuarioExternoDTO crearUsuarioExterno(
            String id, String nombre, String email,
            String rol, String sistema, Boolean activo
    ) {
        UsuarioExternoDTO dto = new UsuarioExternoDTO();
        dto.setId(id);
        dto.setNombre(nombre);
        dto.setEmail(email);
        dto.setRol(rol);
        dto.setSistema(sistema);
        dto.setActivo(activo);
        return dto;
    }
}
