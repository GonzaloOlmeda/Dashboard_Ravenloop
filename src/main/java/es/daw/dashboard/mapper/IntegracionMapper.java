package es.daw.dashboard.mapper;

import es.daw.dashboard.dto.bd.IntegracionDTO;
import es.daw.dashboard.entity.Integracion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IntegracionMapper {

    public IntegracionDTO toDTO(Integracion integracion) {
        if (integracion == null) return null;

        IntegracionDTO dto = new IntegracionDTO();
        dto.setId(integracion.getId());
        dto.setNombreSistema(integracion.getNombreSistema());
        dto.setTipo(integracion.getTipo() != null ? integracion.getTipo().name() : null);
        dto.setEndpointBase(integracion.getEndpointBase());
        dto.setToken(integracion.getToken());
        dto.setFrecuenciaSincronizacion(integracion.getFrecuenciaSincronizacion());

        return dto;
    }

    public Integracion toEntity(IntegracionDTO dto) {
        if (dto == null) return null;

        Integracion integracion = new Integracion();
        integracion.setId(dto.getId());
        integracion.setNombreSistema(dto.getNombreSistema());

        if (dto.getTipo() != null)
            integracion.setTipo(Integracion.TipoIntegracion.valueOf(dto.getTipo()));

        integracion.setEndpointBase(dto.getEndpointBase());
        integracion.setToken(dto.getToken());
        integracion.setFrecuenciaSincronizacion(dto.getFrecuenciaSincronizacion());

        return integracion;
    }
}
