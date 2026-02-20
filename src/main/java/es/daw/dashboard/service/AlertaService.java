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
import es.daw.dashboard.repository.ServidorMvRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final AlertaMapper alertaMapper;
    private final IntegracionRepository integracionRepository;
    private final ServidorMvRepository servidorMvRepository;

    public Page<AlertaDTO> getAlerta(Pageable pageable,
                                      Alerta.CategoriaAlerta categoria,
                                      LocalDateTime fechaInicio,
                                      LocalDateTime fechaFin) {

        Page<Alerta> alertasPage = alertaRepository.findByFilters(categoria, fechaInicio, fechaFin, pageable);

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

        // Validar y asignar servidor si se proporciona el nombre
        if (request.getServidorNombre() != null && !request.getServidorNombre().isBlank()) {
            ServidorMV servidor = servidorMvRepository.findByNombre(request.getServidorNombre())
                    .orElseThrow(() -> new ResourceNotFoundException("Servidor no encontrado con nombre: " + request.getServidorNombre()));
            alerta.setServidorMv(servidor);
        }

        Alerta alertaGuardada = alertaRepository.save(alerta);
        return alertaMapper.toDTO(alertaGuardada);
    }

    public AlertaDTO editarAlerta(Long id, AlertaRequestDTO request) {

        // Buscar la alerta existente
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta no encontrada con ID: " + id));

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

        // Actualizar campos
        try {
            alerta.setCategoria(Alerta.CategoriaAlerta.valueOf(request.getCategoria().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Categoría inválida: " + request.getCategoria() +
                    ". Valores permitidos: CRITICA, ADVERTENCIA, INFORMATIVA");
        }

        alerta.setTipo(request.getTipo());
        alerta.setMensaje(request.getMensaje());

        // Actualizar estado activo si se proporciona en el request
        if (request.getActivo() != null) {
            alerta.setActivo(request.getActivo());
        }

        // Actualizar integración (sistema)
        Integracion integracion = integracionRepository.findById(request.getIntegracionId())
                .orElseThrow(() -> new ResourceNotFoundException("Sistema no encontrado con ID: " + request.getIntegracionId()));
        alerta.setIntegracion(integracion);

        // Validar y actualizar servidor si se proporciona el nombre
        if (request.getServidorNombre() != null && !request.getServidorNombre().isBlank()) {
            ServidorMV servidor = servidorMvRepository.findByNombre(request.getServidorNombre())
                    .orElseThrow(() -> new ResourceNotFoundException("Servidor no encontrado con nombre: " + request.getServidorNombre()));
            alerta.setServidorMv(servidor);
        } else {
            alerta.setServidorMv(null); // Permitir borrar el servidor
        }

        // Mantener la fecha original de la alerta, no actualizarla
        Alerta alertaActualizada = alertaRepository.save(alerta);
        return alertaMapper.toDTO(alertaActualizada);
    }

    @Transactional
    public int eliminarAlertasInactivasAntiguas() {
        // Calcular la fecha límite (30 días antes de ahora)
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(30);

        log.info("Buscando alertas inactivas con fecha anterior a: {}", fechaLimite);

        // Primero obtener las alertas que se van a eliminar para log
        List<Alerta> alertasAEliminar = alertaRepository.findAlertasInactivasParaEliminar(fechaLimite);
        log.info("Encontradas {} alerta(s) candidatas para eliminar", alertasAEliminar.size());

        if (!alertasAEliminar.isEmpty()) {
            for (Alerta alerta : alertasAEliminar) {
                log.info(" - ID: {}, Tipo: {}, Fecha: {}, Activo: {}",
                    alerta.getId(), alerta.getTipo(), alerta.getFechaAlerta(), alerta.getActivo());
            }
        }

        // Eliminar alertas inactivas con más de 30 días
        int eliminadas = alertaRepository.deleteAlertasInactivas(fechaLimite);
        log.info("Total eliminadas: {}", eliminadas);

        return eliminadas;
    }

    public List<AlertaDTO> obtenerAlertasInactivasParaBorrado() {
        // Calcular la fecha límite (30 días antes de ahora)
        LocalDateTime fechaLimite = LocalDateTime.now().minusDays(30);

        // Obtener alertas que serán eliminadas
        List<Alerta> alertas = alertaRepository.findAlertasInactivasParaEliminar(fechaLimite);

        return alertas.stream()
                .map(alertaMapper::toDTO)
                .toList();
    }
}
