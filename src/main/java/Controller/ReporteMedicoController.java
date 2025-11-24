package Controller;

import java.util.List;
import java.util.Optional;

import Model.ReporteMedico;
import Model.Usuario;
import Repository.UsuarioRepository;
import Service.ReporteMedicoService;
import io.javalin.http.Context;

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
            ReporteMedico nuevoReporte = ctx.bodyAsClass(ReporteMedico.class);

            // If a user id is provided via header, prefer that and validate the user exists
            String idUsuarioHeader = ctx.header("Id-Usuario");
            UsuarioRepository usuarioRepo = new UsuarioRepository();
            if (idUsuarioHeader != null && !idUsuarioHeader.isBlank()) {
                try {
                    int idUsuario = Integer.parseInt(idUsuarioHeader.trim());
                    Usuario usuario = usuarioRepo.obtenerPorId(idUsuario);
                    if (usuario == null) {
                        ctx.status(400).result("Usuario no encontrado con id: " + idUsuario);
                        return;
                    }
                    nuevoReporte.setIdUsuario(usuario);
                } catch (NumberFormatException ex) {
                    ctx.status(400).result("Id-Usuario header inválido");
                    return;
                }
            } else {
                // If no header, require user info in body and validate it
                if (nuevoReporte.getIdUsuario() == null || nuevoReporte.getIdUsuario().getIdUsuario() == 0) {
                    ctx.status(400).result("Debe proveer Id-Usuario en el header o en el body (usuario.idUsuario).");
                    return;
                }
                Usuario usuarioBody = usuarioRepo.obtenerPorId(nuevoReporte.getIdUsuario().getIdUsuario());
                if (usuarioBody == null) {
                    ctx.status(400).result("Usuario no encontrado con id: " + nuevoReporte.getIdUsuario().getIdUsuario());
                    return;
                }
                // normalize to full Usuario object
                nuevoReporte.setIdUsuario(usuarioBody);
            }

            ReporteMedico reporteCreado = reporteMedicoService.crearReporte(nuevoReporte);

            if (reporteCreado != null) {
                ctx.status(201).json(reporteCreado);
            } else {
                ctx.status(500).result("No se pudo crear el reporte médico.");
            }

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