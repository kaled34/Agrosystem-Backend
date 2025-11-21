package Controller;

import Model.Usuario;
import io.javalin.http.Context;
import Model.ReporteMedico;
import Service.ReporteMedicoService;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.time.LocalDate;
import Repository.AnimalesRepository;
import Repository.UsuarioRepository;
import Model.Animales;

public class ReporteMedicoController {

    private final ReporteMedicoService reporteMedicoService;

    public ReporteMedicoController(ReporteMedicoService reporteMedicoService) {
        this.reporteMedicoService = reporteMedicoService;
    }

    public void obtenerTodosReportes(Context ctx) {
        List<ReporteMedico> reportes = reporteMedicoService.obtenerTodos();

        if (reportes.isEmpty()) {
            ctx.status(204).result("No hay reportes médicos registrados.");
        } else {
            ctx.status(200).json(reportes);
        }
    }

    public void buscarReportePorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<ReporteMedico> reporte = reporteMedicoService.buscarPorId(id);

        if (reporte.isPresent()) {
            ctx.status(200).json(reporte.get());
        } else {
            ctx.status(404).result("Reporte médico con ID " + id + " no encontrado.");
        }
    }

    public void crearReporte(Context ctx) {
        try {
            Map<String, Object> body = ctx.bodyAsClass(Map.class);

            Map<String, Object> animalData = (Map<String, Object>) body.get("idAnimales");
            Map<String, Object> usuarioData = (Map<String, Object>) body.get("idUsuario");

            int idAnimal = ((Number) animalData.get("idAnimal")).intValue();
            int idUsuario = ((Number) usuarioData.get("idUsuario")).intValue();

            AnimalesRepository animalRepo = new AnimalesRepository();
            UsuarioRepository usuarioRepo = new UsuarioRepository();

            Animales animal = animalRepo.obtenerPorId(idAnimal);
            Usuario usuario = usuarioRepo.obtenerPorId(idUsuario);

            if (animal == null || usuario == null) {
                ctx.status(400).result("Animal o Usuario no encontrado.");
                return;
            }

            ReporteMedico nuevoReporte = new ReporteMedico();
            nuevoReporte.idAnimales = animal;
            nuevoReporte.idUsuario = usuario;
            nuevoReporte.temperatura = ((Number) body.get("temperatura")).doubleValue();
            nuevoReporte.condicionCorporal = (String) body.get("condicionCorporal");
            nuevoReporte.frecuenciaRespiratoria = ((Number) body.get("frecuenciaRespiratoria")).intValue();
            nuevoReporte.fecha = LocalDate.parse((String) body.get("fecha"));
            nuevoReporte.diagnosticoPresuntivo = (String) body.get("diagnosticoPresuntivo");
            nuevoReporte.diagnosticoDefinitivo = (String) body.get("diagnosticoDefinitivo");

            ReporteMedico reporteCreado = reporteMedicoService.crearReporte(nuevoReporte);

            ctx.status(201).json(reporteCreado);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno del servidor al procesar la solicitud: " + e.getMessage());
        }
    }


    public void actualizarReporte(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            ReporteMedico reporteActualizado = ctx.bodyAsClass(ReporteMedico.class);

            if (reporteActualizado.getIdReporte() != id) {
                ctx.status(400).result("El ID del cuerpo no coincide con el ID de la ruta.");
                return;
            }

            ReporteMedico resultado = reporteMedicoService.actualizarReporte(reporteActualizado);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {
                ctx.status(404).result("Reporte médico con ID " + id + " no encontrado para actualizar.");
            }

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }

    public void eliminarReporte(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (reporteMedicoService.eliminarReporte(id)) {
            ctx.status(204).result("Reporte médico eliminado exitosamente.");
        } else {
            ctx.status(404).result("Reporte médico con ID " + id + " no encontrado o no se pudo eliminar.");
        }
    }

    public void buscarReportesPorAnimal(Context ctx) {
        int idAnimal = ctx.queryParamAsClass("idAnimal", Integer.class).get();

        List<ReporteMedico> resultados = reporteMedicoService.buscarPorAnimal(idAnimal);

        if (resultados.isEmpty()) {
            ctx.status(204).result("No se encontraron reportes para el animal con ID: " + idAnimal);
        } else {
            ctx.status(200).json(resultados);
        }
    }
}