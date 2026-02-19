package es.daw.dashboard.dto.bd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegracionSimpleDTO {
    private Long id;
    private String nombreSistema;
    private String tipo;
}
