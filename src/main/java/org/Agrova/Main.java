package org.Agrova;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Map;


// Importar Repositorios (están en paquete Repository)
import Repository.AnimalesRepository;
import Repository.EnfermedadRepository;
import Repository.MedicamentoRepository;
import Repository.TratamientoRepository;
import Repository.ReporteMedicoRepository;
import Repository.TarjetaSaludRepository;
import Repository.PesoRepository;
import Repository.RolRepository;
// Importar Servicios (están en paquete Service)
import Service.AnimalesService;
import Service.EnfermedadService;
import Service.MedicamentoService;
import Service.TratamientoService;
import Service.ReporteMedicoService;
import Service.TarjetaSaludService;
import Service.PesoService;
import Service.RolService;

// Importar Controllers (están en paquete Controller)
import Controller.AnimalesController;
import Controller.EnfermedadController;
import Controller.MedicamentoController;
import Controller.TratamientoController;
import Controller.ReporteMedicoController;
import Controller.TarjetaSaludController;
import Controller.PesoController;
import Controller.RolController;

// Importar Routes (están en paquete Routes)
import Routes.AnimalesRoutes;
import Routes.EnfermedadRoutes;
import Routes.MedicamentoRoutes;
import Routes.TratamientoRoutes;
import Routes.ReporteMedicoRoutes;
import Routes.TarjetaSaludRoutes;
import Routes.PesoRoutes;
import Routes.RolRoutes;

public class Main {
    public static void main(String[] args) {


        AnimalesRepository animalesRepository = new AnimalesRepository();
        EnfermedadRepository enfermedadRepository = new EnfermedadRepository();
        MedicamentoRepository medicamentoRepository = new MedicamentoRepository();
        TratamientoRepository tratamientoRepository = new TratamientoRepository();
        ReporteMedicoRepository reporteMedicoRepository = new ReporteMedicoRepository();
        TarjetaSaludRepository tarjetaSaludRepository = new TarjetaSaludRepository();
        PesoRepository pesoRepository = new PesoRepository();
        RolRepository rolRepository = new RolRepository();

        AnimalesService animalesService = new AnimalesService(animalesRepository);
        EnfermedadService enfermedadService = new EnfermedadService(enfermedadRepository);
        MedicamentoService medicamentoService = new MedicamentoService(medicamentoRepository);
        TratamientoService tratamientoService = new TratamientoService(tratamientoRepository);
        ReporteMedicoService reporteMedicoService = new ReporteMedicoService(reporteMedicoRepository);
        TarjetaSaludService tarjetaSaludService = new TarjetaSaludService(tarjetaSaludRepository);
        PesoService pesoService = new PesoService(pesoRepository);
        RolService rolService = new RolService(rolRepository);

        AnimalesController animalesController = new AnimalesController(animalesService);
        EnfermedadController enfermedadController = new EnfermedadController(enfermedadService);
        MedicamentoController medicamentoController = new MedicamentoController(medicamentoService);
        TratamientoController tratamientoController = new TratamientoController(tratamientoService);
        ReporteMedicoController reporteMedicoController = new ReporteMedicoController(reporteMedicoService);
        TarjetaSaludController tarjetaSaludController = new TarjetaSaludController(tarjetaSaludService);
        PesoController pesoController = new PesoController(pesoService);
        RolController rolController = new RolController(rolService);

        Javalin app = Javalin.create().start(7000);

        System.out.println("🚀 Servidor iniciado en: http://localhost:7000");
        System.out.println("📡 API lista para recibir peticiones");
        System.out.println("================================");


        app.get("/", ctx -> {
            ctx.json(Map.of(
                    "mensaje", "Bienvenido a la API de Agrova",
                    "version", "1.0",
                    "endpoints", Map.of(
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
        System.out.println("  GET    http://localhost:7000/");
        System.out.println("  GET    http://localhost:7000/animales");
        System.out.println("  POST   http://localhost:7000/animales");
        System.out.println("  GET    http://localhost:7000/enfermedades");
        System.out.println("  POST   http://localhost:7000/enfermedades");
        System.out.println("  ... y más");
        System.out.println("================================");
    }
}