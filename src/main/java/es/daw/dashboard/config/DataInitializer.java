package es.daw.dashboard.config;

import es.daw.dashboard.entity.*;
import es.daw.dashboard.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    private final IntegracionRepository integracionRepository;
    private final ServidorMvRepository servidorMvRepository;
    private final AlertaRepository alertaRepository;

    @Override
    public void run(String... args) {
        // Solo insertar si las tablas están vacías
        if (roleRepository.count() == 0) {
            cargarRoles();
        }
        if (usuarioRepository.count() == 0) {
            cargarUsuarios();
        }
        if (integracionRepository.count() == 0) {
            cargarIntegraciones();
        }
        if (servidorMvRepository.count() == 0) {
            cargarServidores();
        }
        if (alertaRepository.count() == 0) {
            cargarAlertas();
        }

    }

    private void cargarRoles() {

        Role admin = new Role();
        admin.setNombre(Role.RoleName.ADMIN);
        roleRepository.save(admin);

        Role user = new Role();
        user.setNombre(Role.RoleName.USER);
        roleRepository.save(user);
    }

    private void cargarUsuarios() {

        Role roleAdmin = roleRepository.findByNombre(Role.RoleName.ADMIN)
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

        Role roleUser = roleRepository.findByNombre(Role.RoleName.USER)
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

        Usuario admin = new Usuario();
        admin.setNombre("Administrador");
        admin.setEmail("admin@empresa.com");
        admin.setPassword("admin123");  // usar BCrypt
        admin.setActivo(true);
        admin.setRole(roleAdmin);
        usuarioRepository.save(admin);

        Usuario user1 = new Usuario();
        user1.setNombre("Juan Pérez");
        user1.setEmail("juan@empresa.com");
        user1.setPassword("user123");
        user1.setActivo(true);
        user1.setRole(roleUser);
        usuarioRepository.save(user1);

        Usuario user2 = new Usuario();
        user2.setNombre("María García");
        user2.setEmail("maria@empresa.com");
        user2.setPassword("user123");
        user2.setActivo(false);
        user2.setRole(roleUser);
        usuarioRepository.save(user2);
    }

    private void cargarIntegraciones() {

        Integracion office365 = new Integracion();
        office365.setNombreSistema("Office 365");
        office365.setTipo(Integracion.TipoIntegracion.identidades);
        office365.setEndpointBase("https://graph.microsoft.com/v1.0");
        office365.setToken("TOKEN_OFFICE365_PENDIENTE");
        office365.setFrecuenciaSincronizacion(900);
        integracionRepository.save(office365);

        Integracion gitlab = new Integracion();
        gitlab.setNombreSistema("GitLab");
        gitlab.setTipo(Integracion.TipoIntegracion.identidades);
        gitlab.setEndpointBase("https://gitlab.com/api/v4");
        gitlab.setToken("TOKEN_GITLAB_PENDIENTE");
        gitlab.setFrecuenciaSincronizacion(900);
        integracionRepository.save(gitlab);

        Integracion jira = new Integracion();
        jira.setNombreSistema("Jira");
        jira.setTipo(Integracion.TipoIntegracion.identidades);
        jira.setEndpointBase("https://tu-empresa.atlassian.net");
        jira.setToken("TOKEN_JIRA_PENDIENTE");
        jira.setFrecuenciaSincronizacion(900);
        integracionRepository.save(jira);

        Integracion zabbix = new Integracion();
        zabbix.setNombreSistema("Zabbix");
        zabbix.setTipo(Integracion.TipoIntegracion.monitorizacion);
        zabbix.setEndpointBase("https://zabbix.empresa.com/api_jsonrpc.php");
        zabbix.setToken("TOKEN_ZABBIX_PENDIENTE");
        zabbix.setFrecuenciaSincronizacion(300);
        integracionRepository.save(zabbix);

    }

    private void cargarServidores() {

        ServidorMV srv1 = new ServidorMV();
        srv1.setNombre("SRV-WEB-01");
        srv1.setTipo(ServidorMV.TipoServidor.SERVIDOR);
        srv1.setEstado(ServidorMV.EstadoServidor.ONLINE);
        srv1.setMemoriaTotal(32);
        srv1.setCapacidadDisco(500);
        servidorMvRepository.save(srv1);

        ServidorMV srv2 = new ServidorMV();
        srv2.setNombre("SRV-DB-01");
        srv2.setTipo(ServidorMV.TipoServidor.SERVIDOR);
        srv2.setEstado(ServidorMV.EstadoServidor.ONLINE);
        srv2.setMemoriaTotal(64);
        srv2.setCapacidadDisco(1000);
        servidorMvRepository.save(srv2);

        ServidorMV srv3 = new ServidorMV();
        srv3.setNombre("SRV-BACKUP-01");
        srv3.setTipo(ServidorMV.TipoServidor.SERVIDOR);
        srv3.setEstado(ServidorMV.EstadoServidor.OFFLINE);
        srv3.setMemoriaTotal(16);
        srv3.setCapacidadDisco(2000);
        servidorMvRepository.save(srv3);

        ServidorMV mv1 = new ServidorMV();
        mv1.setNombre("VM-APP-01");
        mv1.setTipo(ServidorMV.TipoServidor.MV);
        mv1.setEstado(ServidorMV.EstadoServidor.ONLINE);
        mv1.setMemoriaTotal(8);
        mv1.setCapacidadDisco(100);
        servidorMvRepository.save(mv1);

        ServidorMV mv2 = new ServidorMV();
        mv2.setNombre("VM-TEST-01");
        mv2.setTipo(ServidorMV.TipoServidor.MV);
        mv2.setEstado(ServidorMV.EstadoServidor.ONLINE);
        mv2.setMemoriaTotal(4);
        mv2.setCapacidadDisco(50);
        servidorMvRepository.save(mv2);

        ServidorMV mv3 = new ServidorMV();
        mv3.setNombre("VM-TEST-02");
        mv3.setTipo(ServidorMV.TipoServidor.MV);
        mv3.setEstado(ServidorMV.EstadoServidor.OFFLINE);
        mv3.setMemoriaTotal(4);
        mv3.setCapacidadDisco(50);
        servidorMvRepository.save(mv3);

    }

    private void cargarAlertas() {

        Integracion zabbix = integracionRepository.findByNombreSistema("Zabbix")
                .orElseThrow();

        ServidorMV srv1 = servidorMvRepository.findByNombre("SRV-WEB-01")
                .orElseThrow();

        ServidorMV srv2 = servidorMvRepository.findByNombre("SRV-DB-01")
                .orElseThrow();

        // Alerta crítica
        Alerta alerta1 = new Alerta();
        alerta1.setCategoria(Alerta.CategoriaAlerta.CRITICA);
        alerta1.setTipo("SERVICE_DOWN");
        alerta1.setMensaje("Servicio Apache caído en SRV-WEB-01");
        alerta1.setIntegracion(zabbix);
        alerta1.setServidorMv(srv1);
        alertaRepository.save(alerta1);

        // Alerta crítica 2
        Alerta alerta2 = new Alerta();
        alerta2.setCategoria(Alerta.CategoriaAlerta.CRITICA);
        alerta2.setTipo("DISK_FULL");
        alerta2.setMensaje("Disco al 95% de capacidad en SRV-DB-01");
        alerta2.setIntegracion(zabbix);
        alerta2.setServidorMv(srv2);
        alertaRepository.save(alerta2);

        // Alerta advertencia
        Alerta alerta3 = new Alerta();
        alerta3.setCategoria(Alerta.CategoriaAlerta.ADVERTENCIA);
        alerta3.setTipo("CPU_HIGH");
        alerta3.setMensaje("CPU al 85% durante 10 minutos en SRV-DB-01");
        alerta3.setIntegracion(zabbix);
        alerta3.setServidorMv(srv2);
        alertaRepository.save(alerta3);

        // Alerta advertencia 2
        Alerta alerta4 = new Alerta();
        alerta4.setCategoria(Alerta.CategoriaAlerta.ADVERTENCIA);
        alerta4.setTipo("MEMORY_HIGH");
        alerta4.setMensaje("Memoria RAM al 80% en SRV-WEB-01");
        alerta4.setIntegracion(zabbix);
        alerta4.setServidorMv(srv1);
        alertaRepository.save(alerta4);

        // Alerta informativa
        Alerta alerta5 = new Alerta();
        alerta5.setCategoria(Alerta.CategoriaAlerta.INFORMATIVA);
        alerta5.setTipo("BACKUP_OK");
        alerta5.setMensaje("Backup nocturno completado correctamente");
        alerta5.setIntegracion(zabbix);
        alerta5.setServidorMv(null);
        alertaRepository.save(alerta5);

    }
}
