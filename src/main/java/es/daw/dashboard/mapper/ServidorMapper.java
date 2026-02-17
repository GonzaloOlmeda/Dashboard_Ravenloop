package es.daw.dashboard.mapper;

import es.daw.dashboard.dto.bd.ServidorDTO;
import es.daw.dashboard.entity.ServidorMV;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServidorMapper {

    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "estado", target = "estado")
    ServidorDTO toDTO(ServidorMV servidor);

    @Mapping(source = "tipo", target = "tipo")
    @Mapping(source = "estado", target = "estado")
    ServidorMV toEntity(ServidorDTO dto);
}
