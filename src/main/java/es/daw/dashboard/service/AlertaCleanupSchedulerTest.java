package es.daw.dashboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * SCHEDULER DE PRUEBA - Solo para desarrollo
 * Este scheduler se ejecuta cada minuto para poder probar el borrado automático
 * sin tener que esperar 24 horas.
 *
 * ⚠️ IMPORTANTE: Comentar o eliminar este archivo en producción ⚠️
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AlertaCleanupSchedulerTest {

    private final AlertaService alertaService;

    /**
     * Tarea programada que se ejecuta cada minuto SOLO PARA PRUEBAS
     *
     * Cron expression: "0 * * * * ?" significa:
     * - 0 segundos
     * - cada minuto
     * - todas las horas
     * - todos los días del mes
     * - todos los meses
     * - cualquier día de la semana
     */
    @Scheduled(cron = "0 * * * * ?")
    public void limpiarAlertasInactivasAntiguasTest() {
        log.info("🧪 [TEST] Ejecutando limpieza automática de alertas inactivas antiguas...");

        try {
            int alertasEliminadas = alertaService.eliminarAlertasInactivasAntiguas();

            if (alertasEliminadas > 0) {
                log.warn("✅ [TEST] Limpieza completada: {} alerta(s) eliminada(s)", alertasEliminadas);
            } else {
                log.debug("ℹ️  [TEST] No hay alertas inactivas para eliminar");
            }

        } catch (Exception e) {
            log.error("❌ [TEST] Error durante la limpieza automática de alertas: {}", e.getMessage(), e);
        }
    }
}
