package es.daw.dashboard.controller;

import es.daw.dashboard.dto.bd.AlertaDTO;
import es.daw.dashboard.dto.bd.AlertaRequestDTO;
import es.daw.dashboard.dto.bd.CategoriaDTO;
import es.daw.dashboard.dto.bd.IntegracionSimpleDTO;
import es.daw.dashboard.dto.bd.ServidorSimpleDTO;
import es.daw.dashboard.entity.Alerta;
import es.daw.dashboard.exception.BadRequestException;
import es.daw.dashboard.repository.IntegracionRepository;
import es.daw.dashboard.repository.ServidorMvRepository;
import es.daw.dashboard.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;
    private final IntegracionRepository integracionRepository;
    private final ServidorMvRepository servidorMvRepository;

    @GetMapping
    public ResponseEntity<Page<AlertaDTO>> getAlertas(
            @RequestParam(required = false) String categoria,
            Pageable pageable) {

        Alerta.CategoriaAlerta categoriaEnum = null;
        if (categoria != null && !categoria.isBlank()) {
            try {
                categoriaEnum = Alerta.CategoriaAlerta.valueOf(categoria.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Categoría inválida: " + categoria +
                        ". Valores permitidos: CRITICA, ADVERTENCIA, INFORMATIVA");
            }
        }

        return ResponseEntity.ok(alertaService.getAlerta(pageable, categoriaEnum, null, null));
    }



    @GetMapping("/fecha")
    public ResponseEntity<Page<AlertaDTO>> getAlertasPorFecha(
            @RequestParam String fecha,
            Pageable pageable) {

        LocalDateTime fechaInicio;
        LocalDateTime fechaFin;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
            fechaInicio = fechaLocal.atStartOfDay();
            fechaFin = fechaLocal.atTime(23, 59, 59);
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Formato de fecha inválido: " + fecha +
                    ". Formato esperado: yyyy/MM/dd (ej: 2026/02/19)");
        }

        return ResponseEntity.ok(alertaService.getAlerta(pageable, null, fechaInicio, fechaFin));
    }

    @PostMapping
    public ResponseEntity<AlertaDTO> crearAlerta(@RequestBody AlertaRequestDTO request) {
        AlertaDTO nuevaAlerta = alertaService.crearAlerta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaAlerta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaDTO> editarAlerta(
            @PathVariable Long id,
            @RequestBody AlertaRequestDTO request) {
        AlertaDTO alertaActualizada = alertaService.editarAlerta(id, request);
        return ResponseEntity.ok(alertaActualizada);
    }

    @GetMapping("/sistemas")
    public ResponseEntity<List<IntegracionSimpleDTO>> getSistemasDisponibles() {
        List<IntegracionSimpleDTO> sistemas = integracionRepository.findAll().stream()
                .map(integracion -> new IntegracionSimpleDTO(
                        integracion.getId(),
                        integracion.getNombreSistema(),
                        integracion.getTipo().name()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(sistemas);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> getCategoriasDisponibles() {
        List<CategoriaDTO> categorias = Arrays.stream(Alerta.CategoriaAlerta.values())
                .map(cat -> new CategoriaDTO(cat.name(), formatearCategoria(cat.name())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/servidores")
    public ResponseEntity<List<ServidorSimpleDTO>> getServidoresDisponibles() {
        List<ServidorSimpleDTO> servidores = servidorMvRepository.findAll().stream()
                .map(servidor -> new ServidorSimpleDTO(
                        servidor.getId(),
                        servidor.getNombre(),
                        servidor.getTipo().name()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(servidores);
    }

    private String formatearCategoria(String categoria) {
        return switch (categoria) {
            case "CRITICA" -> "Crítica";
            case "ADVERTENCIA" -> "Advertencia";
            case "INFORMATIVA" -> "Informativa";
            default -> categoria;
        };
    }
}
