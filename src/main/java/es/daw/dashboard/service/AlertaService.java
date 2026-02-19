package es.daw.dashboard.service;

import es.daw.dashboard.dto.bd.AlertaDTO;
import es.daw.dashboard.dto.bd.AlertaRequestDTO;
import es.daw.dashboard.entity.Alerta;
import es.daw.dashboard.entity.Integracion;
import es.daw.dashboard.entity.ServidorMV;
import es.daw.dashboard.exception.BadRequestException;
import es.daw.dashboard.exception.ResourceNotFoundException;
import es.daw.dashboard.mapper.AlertaMapper;
import es.daw.dashboard.repository.AlertaRepository;
import es.daw.dashboard.repository.IntegracionRepository;
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
    private final IntegracionRepository integracionRepository;

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

    public AlertaDTO crearAlerta(AlertaRequestDTO request) {

        // Validaciones
        if (request.getCategoria() == null || request.getCategoria().isBlank()) {
            throw new BadRequestException("La categoría es obligatoria");
        }

        if (request.getTipo() == null || request.getTipo().isBlank()) {
            throw new BadRequestException("El tipo es obligatorio");
        }

        if (request.getMensaje() == null || request.getMensaje().isBlank()) {
            throw new BadRequestException("El mensaje es obligatorio");
        }

        if (request.getIntegracionId() == null) {
            throw new BadRequestException("El sistema (integración) es obligatorio");
        }

        Alerta alerta = new Alerta();

        // Validar y asignar categoría
        try {
            alerta.setCategoria(Alerta.CategoriaAlerta.valueOf(request.getCategoria().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Categoría inválida: " + request.getCategoria() +
                    ". Valores permitidos: CRITICA, ADVERTENCIA, INFORMATIVA");
        }

        alerta.setTipo(request.getTipo());
        alerta.setMensaje(request.getMensaje());
        alerta.setActivo(true); // Estado activo por defecto
        alerta.setFechaAlerta(LocalDateTime.now());

        // Asignar integración (sistema) - OBLIGATORIO
        Integracion integracion = integracionRepository.findById(request.getIntegracionId())
                .orElseThrow(() -> new ResourceNotFoundException("Sistema no encontrado con ID: " + request.getIntegracionId()));
        alerta.setIntegracion(integracion);

        Alerta alertaGuardada = alertaRepository.save(alerta);
        return alertaMapper.toDTO(alertaGuardada);
    }
}
