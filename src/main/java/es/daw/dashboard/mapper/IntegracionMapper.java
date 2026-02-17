package es.daw.dashboard.mapper;

import es.daw.dashboard.dto.bd.IntegracionDTO;
import es.daw.dashboard.entity.Integracion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IntegracionMapper {

    @Mapping(source = "tipo", target = "tipo")
    IntegracionDTO toDTO(Integracion integracion);

    @Mapping(source = "tipo", target = "tipo")
    Integracion toEntity(IntegracionDTO dto);
}
