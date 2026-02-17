package es.daw.dashboard.mapper;

import es.daw.dashboard.dto.bd.ServidorDTO;
import es.daw.dashboard.entity.ServidorMV;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServidorMapper {

    public ServidorDTO toDTO(ServidorMV servidor) {
        if (servidor == null) return null;

        ServidorDTO dto = new ServidorDTO();
        dto.setId(servidor.getId());
        dto.setNombre(servidor.getNombre());
        dto.setTipo(servidor.getTipo() != null ? servidor.getTipo().name() : null);
        dto.setEstado(servidor.getEstado() != null ? servidor.getEstado().name() : null);
        dto.setMemoriaTotal(servidor.getMemoriaTotal());
        dto.setCapacidadDisco(servidor.getCapacidadDisco());

        // Métricas vienen de API externa → no se mapean desde la entidad

        return dto;
    }

    public ServidorMV toEntity(ServidorDTO dto) {
        if (dto == null) return null;

        ServidorMV servidor = new ServidorMV();
        servidor.setId(dto.getId());
        servidor.setNombre(dto.getNombre());

        if (dto.getTipo() != null)
            servidor.setTipo(ServidorMV.TipoServidor.valueOf(dto.getTipo()));

        if (dto.getEstado() != null)
            servidor.setEstado(ServidorMV.EstadoServidor.valueOf(dto.getEstado()));

        servidor.setMemoriaTotal(dto.getMemoriaTotal());
        servidor.setCapacidadDisco(dto.getCapacidadDisco());

        return servidor;
    }
}
