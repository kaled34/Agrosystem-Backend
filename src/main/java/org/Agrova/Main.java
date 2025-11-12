package org.Agrova;

import Config.ConfigDB;
import Model.Animales;
import io.javalin.Javalin;
import java.util.Map;

import Repository.*;
import Service.*;
import Controller.*;
import Routes.*;

public class Main {
    public static void main(String[] args) {

        ConfigDB.getDataSource();

        // Repositorios
        AnimalesRepository animalesRepository = new AnimalesRepository();
        EnfermedadRepository enfermedadRepository = new EnfermedadRepository();
        MedicamentoRepository medicamentoRepository = new MedicamentoRepository();
        TratamientoRepository tratamientoRepository = new TratamientoRepository();
        ReporteMedicoRepository reporteMedicoRepository = new ReporteMedicoRepository();
        TarjetaSaludRepository tarjetaSaludRepository = new TarjetaSaludRepository();
        PesoRepository pesoRepository = new PesoRepository();
        RolRepository rolRepository = new RolRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();

        // Servicios
        AnimalesService animalesService = new AnimalesService(animalesRepository);
        EnfermedadService enfermedadService = new EnfermedadService(enfermedadRepository);
        MedicamentoService medicamentoService = new MedicamentoService(medicamentoRepository);
        TratamientoService tratamientoService = new TratamientoService(tratamientoRepository);
        ReporteMedicoService reporteMedicoService = new ReporteMedicoService(reporteMedicoRepository);
        TarjetaSaludService tarjetaSaludService = new TarjetaSaludService(tarjetaSaludRepository);
        PesoService pesoService = new PesoService(pesoRepository);
        RolService rolService = new RolService(rolRepository);

        // Controladores
        AnimalesController animalesController = new AnimalesController(animalesService);
        EnfermedadController enfermedadController = new EnfermedadController(enfermedadService);
        MedicamentoController medicamentoController = new MedicamentoController(medicamentoService);
        TratamientoController tratamientoController = new TratamientoController(tratamientoService);
        ReporteMedicoController reporteMedicoController = new ReporteMedicoController(reporteMedicoService);
        TarjetaSaludController tarjetaSaludController = new TarjetaSaludController(tarjetaSaludService);
        PesoController pesoController = new PesoController(pesoService);
        RolController rolController = new RolController(rolService);
        UsuarioController usuarioController = new UsuarioController(usuarioRepository);

        // Configurar Javalin con CORS
        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.anyHost();
                });
            });
        }).start(7000);

        System.out.println("🚀 Servidor iniciado en: http://localhost:7000");
        System.out.println("📡 API lista para recibir peticiones");
        System.out.println("🗄️   Conectado a MySQL");
        System.out.println("================================");

        // Ruta principal
        app.get("/", ctx -> {
            ctx.json(Map.of(
                    "mensaje", "Bienvenido a la API de Agrova",
                    "version", "1.0",
                    "database", "MySQL",
                    "endpoints", Map.of(
                            "login", "/login",
                            "usuarios", "/usuarios",
                            "animales", "/animales",
                            "enfermedades", "/enfermedades",
                            "medicamentos", "/medicamentos",
                            "tratamientos", "/tratamientos",
                            "reportes", "/reportes",
                            "tarjetas", "/tarjetas",
                            "pesos", "/pesos",
                            "roles", "/roles"
                    )
            ));
        });

        // Ruta de login
        app.post("/login", usuarioController::login);

        // Registrar rutas
        new UsuarioRoutes(usuarioController).register(app);
        new AnimalesRoutes(animalesController).register(app);
        new EnfermedadRoutes(enfermedadController).register(app);
        new MedicamentoRoutes(medicamentoController).register(app);
        new TratamientoRoutes(tratamientoController).register(app);
        new ReporteMedicoRoutes(reporteMedicoController).register(app);
        new TarjetaSaludRoutes(tarjetaSaludController).register(app);
        new PesoRoutes(pesoController).register(app);
        new RolRoutes(rolController).register(app);



        System.out.println("✅ Todas las rutas registradas exitosamente");
        System.out.println("================================");
        System.out.println("Endpoints disponibles:");
        System.out.println("  POST   http://localhost:7000/login");
        System.out.println("  GET    http://localhost:7000/usuarios");
        System.out.println("  GET    http://localhost:7000/animales");
        System.out.println("  GET    http://localhost:7000/roles");
        System.out.println("================================");

        // Cerrar conexión al detener el servidor
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n🛑 Cerrando servidor...");
            ConfigDB.close();
            app.stop();
            System.out.println("✅ Servidor cerrado correctamente");
        }));
    }
}

