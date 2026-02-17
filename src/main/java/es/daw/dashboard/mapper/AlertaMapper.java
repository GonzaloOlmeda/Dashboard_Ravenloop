package es.daw.dashboard.mapper;

import es.daw.dashboard.dto.bd.AlertaDTO;
import es.daw.dashboard.entity.Alerta;
import es.daw.dashboard.entity.Integracion;
import es.daw.dashboard.entity.ServidorMV;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AlertaMapper {

    // ENTITY → DTO
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "origen", target = "origen", qualifiedByName = "intToString")
    @Mapping(source = "servidorMv.id", target = "servidorId")
    @Mapping(source = "servidorMv.nombre", target = "servidorNombre")
    @Mapping(source = "integracion.id", target = "integracionId")
    @Mapping(source = "integracion.nombreSistema", target = "integracionNombre")
    AlertaDTO toDTO(Alerta alerta);

    // DTO → ENTITY
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "origen", target = "origen", qualifiedByName = "stringToInt")
    @Mapping(source = "servidorId", target = "servidorMv", qualifiedByName = "mapServidor")
    @Mapping(source = "integracionId", target = "integracion", qualifiedByName = "mapIntegracion")
    Alerta toEntity(AlertaDTO dto);

    @Named("stringToInt")
    default Integer stringToInt(String value) {
        try { return value != null ? Integer.parseInt(value) : null; }
        catch (NumberFormatException e) { return null; }
    }

    @Named("intToString")
    default String intToString(Integer value) {
        return value != null ? value.toString() : null;
    }

    @Named("mapServidor")
    default ServidorMV mapServidor(Long id) {
        if (id == null) return null;
        ServidorMV s = new ServidorMV();
        s.setId(id);
        return s;
    }

    @Named("mapIntegracion")
    default Integracion mapIntegracion(Long id) {
        if (id == null) return null;
        Integracion i = new Integracion();
        i.setId(id);
        return i;
    }
}
