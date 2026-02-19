package es.daw.dashboard.service;

import es.daw.dashboard.dto.bd.AlertaDTO;
import es.daw.dashboard.entity.Alerta;
import es.daw.dashboard.entity.ServidorMV;
import es.daw.dashboard.exception.ResourceNotFoundException;
import es.daw.dashboard.mapper.AlertaMapper;
import es.daw.dashboard.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final AlertaMapper alertaMapper;

    public Page<AlertaDTO> getAlerta(Pageable pageable,
                                      Alerta.CategoriaAlerta categoria,
                                      ServidorMV.TipoServidor tipoServidor,
                                      LocalDateTime fechaInicio,
                                      LocalDateTime fechaFin) {

        Page<Alerta> alertasPage = alertaRepository.findByFilters(categoria, tipoServidor, fechaInicio, fechaFin, pageable);

        if (alertasPage.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron alertas");
        }

        return alertasPage.map(alertaMapper::toDTO);
    }
}
