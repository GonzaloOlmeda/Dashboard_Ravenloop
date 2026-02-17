package es.daw.dashboard.dto.APIs;

import lombok.Data;

@Data
public class JiraSummaryDTO {
    private Long totalUsers;
    private Long activeUsers;
    private Long openIssues;
    private Long totalProjects;
}
