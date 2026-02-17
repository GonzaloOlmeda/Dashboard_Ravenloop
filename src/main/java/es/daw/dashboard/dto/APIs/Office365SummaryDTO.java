package es.daw.dashboard.dto.APIs;

import lombok.Data;

@Data
public class Office365SummaryDTO {

    private Long totalUsers;
    private Long activeUsersLast30Days;
    private Long licensesAssigned;
}
