package es.daw.dashboard.dto.bd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    private String value;      // "CRITICA"
    private String label;      // "Crítica" (para mostrar en el frontend)
}
