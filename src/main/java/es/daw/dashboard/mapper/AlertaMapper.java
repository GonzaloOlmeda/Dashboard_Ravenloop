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
        dto.setActivo(alerta.getActivo());
        dto.setFechaAlerta(alerta.getFechaAlerta());

        // Integración relacionada
        if (alerta.getIntegracion() != null) {
            dto.setIntegracionId(alerta.getIntegracion().getId());
            dto.setIntegracionNombre(alerta.getIntegracion().getNombreSistema());
        }

        // Nombre del servidor a través de la relación
        if (alerta.getServidorMv() != null) {
            dto.setServidorNombre(alerta.getServidorMv().getNombre());
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
        alerta.setActivo(dto.getActivo());
        alerta.setFechaAlerta(dto.getFechaAlerta());

        // Integración relacionada (solo ID)
        if (dto.getIntegracionId() != null) {
            Integracion integracion = new Integracion();
            integracion.setId(dto.getIntegracionId());
            alerta.setIntegracion(integracion);
        }


        return alerta;
    }
}

