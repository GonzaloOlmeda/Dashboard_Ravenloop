package es.daw.dashboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Servicio programado para limpiar automáticamente alertas inactivas antiguas.
 * Se ejecuta diariamente a las 2:00 AM.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlertaCleanupScheduler {

    private final AlertaService alertaService;

    /**
     * Tarea programada que se ejecuta todos los días a las 2:00 AM
     * para eliminar alertas inactivas con más de 30 días de antigüedad.
     *
     * Cron expression: "0 0 2 * * ?" significa:
     * - 0 segundos
     * - 0 minutos
     * - 2 horas (2 AM)
     * - todos los días del mes
     * - todos los meses
     * - cualquier día de la semana
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void limpiarAlertasInactivasAntiguas() {
        log.info("🗑️  Iniciando limpieza automática de alertas inactivas antiguas...");

        try {
            int alertasEliminadas = alertaService.eliminarAlertasInactivasAntiguas();

            if (alertasEliminadas > 0) {
                log.info("✅ Limpieza completada: {} alerta(s) eliminada(s)", alertasEliminadas);
            } else {
                log.info("ℹ️  No hay alertas inactivas para eliminar");
            }

        } catch (Exception e) {
            log.error("❌ Error durante la limpieza automática de alertas: {}", e.getMessage(), e);
        }
    }

    /**
     * Método alternativo: ejecutar cada 24 horas desde el inicio de la aplicación
     * (comentado por defecto, usar solo si prefieres este enfoque)
     */
    // @Scheduled(fixedRate = 86400000) // 24 horas en milisegundos
    // public void limpiarAlertasPorIntervalo() {
    //     limpiarAlertasInactivasAntiguas();
    // }
}
