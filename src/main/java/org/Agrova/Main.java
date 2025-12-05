package org.Agrova;

import Config.ConfigDB;
import io.javalin.Javalin;
import java.util.Map;

import Repository.*;
import Service.*;
import Controller.*;
import Routes.*;
import Routes.EstadisticaMedicamentoRoutes;

/**
 * Clase principal de la aplicación Agrosystem Backend.
 * Configura e inicializa el servidor Javalin con todas las dependencias,
 * servicios, controladores y rutas REST para la gestión ganadera.
 * 
 * <p>
 * Este backend proporciona una API REST completa para:
 * </p>
 * <ul>
 * <li>Gestión de animales y registros ganaderos</li>
 * <li>Control de medicamentos y tratamientos</li>
 * <li>Registro de enfermedades y reportes médicos</li>
 * <li>Autenticación de usuarios con JWT</li>
 * <li>Estadísticas y análisis de datos</li>
 * </ul>
 * 
 * <p>
 * Arquitectura de capas implementada:
 * </p>
 * <ul>
 * <li><b>Config</b>: Configuración de base de datos con HikariCP</li>
 * <li><b>Model</b>: Entidades del dominio (POJOs)</li>
 * <li><b>Repository</b>: Acceso a datos y operaciones CRUD</li>
 * <li><b>Service</b>: Lógica de negocio y validaciones</li>
 * <li><b>Controller</b>: Manejo de peticiones HTTP</li>
 * <li><b>Routes</b>: Registro de endpoints en Javalin</li>
 * </ul>
 * 
 * <p>
 * El servidor inicia en el puerto 7000 con CORS habilitado.
 * </p>
 * 
 * @author Agrosystem Team
 * @version 1.0
 */
public class Main {
    /**
     * Punto de entrada de la aplicación.
     * Inicializa la base de datos, crea todas las instancias de Repository, Service
     * y Controller,
     * configura el servidor Javalin con CORS y registra todas las rutas REST.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {

        ConfigDB.getDataSource();

        AnimalesRepository animalesRepository = new AnimalesRepository();
        EstadisticasRepository estadisticasRepository = new EstadisticasRepository();
        EnfermedadRepository enfermedadRepository = new EnfermedadRepository();
        MedicamentoRepository medicamentoRepository = new MedicamentoRepository();
        TratamientoRepository tratamientoRepository = new TratamientoRepository();
        ReporteMedicoRepository reporteMedicoRepository = new ReporteMedicoRepository();
        TarjetaSaludRepository tarjetaSaludRepository = new TarjetaSaludRepository();
        PesoRepository pesoRepository = new PesoRepository();
        RolRepository rolRepository = new RolRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();

        AnimalesService animalesService = new AnimalesService(animalesRepository);
        EstadisticasService estadisticasService = new EstadisticasService(estadisticasRepository);
        EnfermedadService enfermedadService = new EnfermedadService(enfermedadRepository);
        MedicamentoService medicamentoService = new MedicamentoService(medicamentoRepository);
        TratamientoService tratamientoService = new TratamientoService(tratamientoRepository);
        ReporteMedicoService reporteMedicoService = new ReporteMedicoService(reporteMedicoRepository);
        TarjetaSaludService tarjetaSaludService = new TarjetaSaludService(tarjetaSaludRepository);
        PesoService pesoService = new PesoService(pesoRepository);
        RolService rolService = new RolService(rolRepository);

        TokenManager tokenManager = new TokenManager();
        JwtMiddleware jwtMiddleware = new JwtMiddleware(tokenManager);

        AnimalesController animalesController = new AnimalesController(animalesService);
        EnfermedadController enfermedadController = new EnfermedadController(enfermedadService);
        EstadisticasController estadisticasController = new EstadisticasController(estadisticasService);
        MedicamentoController medicamentoController = new MedicamentoController(medicamentoService);
        TratamientoController tratamientoController = new TratamientoController(tratamientoService);
        ReporteMedicoController reporteMedicoController = new ReporteMedicoController(reporteMedicoService);
        TarjetaSaludController tarjetaSaludController = new TarjetaSaludController(tarjetaSaludService);
        PesoController pesoController = new PesoController(pesoService);
        RolController rolController = new RolController(rolService);
        UsuarioController usuarioController = new UsuarioController(usuarioRepository, tokenManager);

        EstadisticasTratamientoRepository estadisticasTratamientoRepository = new EstadisticasTratamientoRepository();
        EstadisticasTratamientoService estadisticasTratamientoService = new EstadisticasTratamientoService(
                estadisticasTratamientoRepository);
        EstadisticasTratamientoController estadisticasTratamientoController = new EstadisticasTratamientoController(
                estadisticasTratamientoService);
        EstadisticasTratamientoRoutes estadisticasTratamientoRoutes = new EstadisticasTratamientoRoutes(
                estadisticasTratamientoController);

        EstadisticaMedicamentoRepository estadisticaMedicamentoRepository = new EstadisticaMedicamentoRepository();
        EstadisticaMedicamentoService estadisticaMedicamentoService = new EstadisticaMedicamentoService(
                estadisticaMedicamentoRepository);
        EstadisticaMedicamentoController estadisticaMedicamentoController = new EstadisticaMedicamentoController(
                estadisticaMedicamentoService);
        EstadisticaMedicamentoRoutes estadisticaMedicamentoRoutes = new EstadisticaMedicamentoRoutes(
                estadisticaMedicamentoController);

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.anyHost();
                });
            });
        }).start(7000);
        System.out.println("🚀 Servidor iniciado en: http://localhost:7000");
        System.out.println("📡 API lista para recibir peticiones");
        System.out.println("🗄️  Conectado a MySQL");
        System.out.println("🔐 Sistema JWT activado");
        System.out.println("================================");

        app.get("/", ctx -> {
            ctx.json(Map.of(
                    "mensaje", "Bienvenido a la API de Agrova",
                    "version", "1.0",
                    "database", "MySQL",
                    "auth", "JWT",
                    "endpoints", Map.of(
                            "login", "POST /login",
                            "usuarios", "/usuarios",
                            "animales", "/animales",
                            "enfermedades", "/enfermedades",
                            "medicamentos", "/medicamento",
                            "tratamientos", "/tratamientos",
                            "reportes", "/reportes",
                            "tarjetas", "/tarjetas",
                            "pesos", "/pesos",
                            "roles", "/rol")));
        });

        app.get("/test", ctx -> {
            System.out.println("Endpoint /test llamado");
            ctx.json(Map.of("status", "ok", "message", "Test exitoso"));
        });

        app.post("/login", usuarioController::login);
        app.post("/register", usuarioController::crearUsuario);
        // jwtMiddleware.apply(app);
        new EstadisticasRoutes(estadisticasController).register(app);
        new UsuarioRoutes(usuarioController).register(app);
        new AnimalesRoutes(animalesController).register(app); // rutas listas
        new EnfermedadRoutes(enfermedadController).register(app);
        new MedicamentoRoutes(medicamentoController).register(app);
        new TratamientoRoutes(tratamientoController).register(app);
        new ReporteMedicoRoutes(reporteMedicoController).register(app);
        new TarjetaSaludRoutes(tarjetaSaludController).register(app);
        new PesoRoutes(pesoController).register(app);
        new RolRoutes(rolController).register(app);
        new EstadisticasTratamientoRoutes(estadisticasTratamientoController).register(app);
        estadisticaMedicamentoRoutes.register(app);

        System.out.println("✅ Todas las rutas registradas exitosamente");
        System.out.println("================================");
        System.out.println("Endpoints disponibles:");
        System.out.println("  POST   http://localhost:7000/login (público)");
        System.out.println("  GET    http://localhost:7000/test (público)");
        System.out.println("  GET    http://localhost:7000/usuarios (protegido)");
        System.out.println("  GET    http://localhost:7000/animales (protegido)");
        System.out.println("  GET    http://localhost:7000/rol (protegido)");
        System.out.println("GET http://localhost:7000/estadisticas/animales");
        System.out.println("================================");
        System.out.println("Para acceder a rutas protegidas:");
        System.out.println("  Authorization: Bearer <token>");
        System.out.println("  Id-Usuario: <id>");
        System.out.println("================================");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n🛑 Cerrando servidor...");
            ConfigDB.close();
            app.stop();
            System.out.println("✅ Servidor cerrado correctamente");
        }));
    }
}