package es.daw.dashboard.mapper;

import es.daw.dashboard.dto.bd.AlertaDTO;
import es.daw.dashboard.entity.Alerta;
import es.daw.dashboard.entity.Integracion;
import es.daw.dashboard.entity.ServidorMV;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlertaMapper {

    // ENTITY → DTO
    public AlertaDTO toDTO(Alerta alerta) {
        if (alerta == null) return null;

        AlertaDTO dto = new AlertaDTO();
        dto.setId(alerta.getId());
        dto.setCategoria(alerta.getCategoria() != null ? alerta.getCategoria().name() : null);
        dto.setTipo(alerta.getTipo());
        dto.setMensaje(alerta.getMensaje());
        dto.setOrigen(alerta.getOrigen() != null ? alerta.getOrigen().toString() : null);
        dto.setFechaAlerta(alerta.getFechaAlerta());

        // Servidor relacionado
        if (alerta.getServidorMv() != null) {
            dto.setServidorId(alerta.getServidorMv().getId());
            dto.setServidorNombre(alerta.getServidorMv().getNombre());
        }

        // Integración relacionada
        if (alerta.getIntegracion() != null) {
            dto.setIntegracionId(alerta.getIntegracion().getId());
            dto.setIntegracionNombre(alerta.getIntegracion().getNombreSistema());
        }

        return dto;
    }

    // DTO → ENTITY
    public Alerta toEntity(AlertaDTO dto) {
        if (dto == null) return null;

        Alerta alerta = new Alerta();
        alerta.setId(dto.getId());
        alerta.setCategoria(dto.getCategoria() != null ?
                Alerta.CategoriaAlerta.valueOf(dto.getCategoria()) : null);
        alerta.setTipo(dto.getTipo());
        alerta.setMensaje(dto.getMensaje());
        alerta.setOrigen(dto.getOrigen() != null ? Integer.parseInt(dto.getOrigen()) : null);
        alerta.setFechaAlerta(dto.getFechaAlerta());

        // Servidor relacionado (solo ID)
        if (dto.getServidorId() != null) {
            ServidorMV servidor = new ServidorMV();
            servidor.setId(dto.getServidorId());
            alerta.setServidorMv(servidor);
        }

        // Integración relacionada (solo ID)
        if (dto.getIntegracionId() != null) {
            Integracion integracion = new Integracion();
            integracion.setId(dto.getIntegracionId());
            alerta.setIntegracion(integracion);
        }

        return alerta;
    }
}

