package es.daw.dashboard.controller;

import es.daw.dashboard.dto.bd.AlertaDTO;
import es.daw.dashboard.entity.Alerta;
import es.daw.dashboard.exception.BadRequestException;
import es.daw.dashboard.service.AlertaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<Page<AlertaDTO>> getAlertas(Pageable pageable) {
        return ResponseEntity.ok(alertaService.getAlerta(pageable, null, null, null, null));
    }

    @GetMapping("/categoria")
    public ResponseEntity<Page<AlertaDTO>> getAlertasPorCategoria(
            @RequestParam String categoria,
            Pageable pageable) {

        Alerta.CategoriaAlerta categoriaEnum;
        try {
            categoriaEnum = Alerta.CategoriaAlerta.valueOf(categoria.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Categoría inválida: " + categoria +
                    ". Valores permitidos: CRITICA, ADVERTENCIA, INFORMATIVA");
        }

        return ResponseEntity.ok(alertaService.getAlerta(pageable, categoriaEnum, null, null, null));
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

        return ResponseEntity.ok(alertaService.getAlerta(pageable, null, null, fechaInicio, fechaFin));
    }
}
