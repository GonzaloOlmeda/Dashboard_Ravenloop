package es.daw.dashboard.dto.bd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServidorSimpleDTO {
    private Long id;
    private String nombre;
    private String tipo; // "SERVIDOR" o "MV"
}
