package Controller;

import io.javalin.http.Context;
import Model.Analisis;
import Service.AnalisisService;
import java.util.List;
import java.util.Optional;

public class AnalisisController {

    private final AnalisisService analisisService;

    public AnalisisController(AnalisisService analisisService) {
        this.analisisService = analisisService;
    }

    public void obtenerTodosAnalisis(Context ctx) {
        List<Analisis> analisis = analisisService.obtenerTodos();

        if (analisis.isEmpty()) {
            ctx.status(204).result("No hay análisis registrados.");
        } else {
            ctx.status(200).json(analisis);
        }
    }

    public void buscarAnalisisPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<Analisis> analisis = analisisService.buscarPorId(id);

        if (analisis.isPresent()) {
            ctx.status(200).json(analisis.get());
        } else {
            ctx.status(404).result("Análisis con ID " + id + " no encontrado.");
        }
    }

    public void crearAnalisis(Context ctx) {
        try {
            Analisis nuevoAnalisis = ctx.bodyAsClass(Analisis.class);

            Analisis analisisCreado = analisisService.crearAnalisis(nuevoAnalisis);

            ctx.status(201).json(analisisCreado);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno del servidor al procesar la solicitud: " + e.getMessage());
        }
    }

    public void actualizarAnalisis(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Analisis analisisActualizado = ctx.bodyAsClass(Analisis.class);

            if (analisisActualizado.getIdAnalisis() != id) {
                ctx.status(400).result("El ID del cuerpo no coincide con el ID de la ruta.");
                return;
            }

            Analisis resultado = analisisService.actualizarAnalisis(analisisActualizado);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {
                ctx.status(404).result("Análisis con ID " + id + " no encontrado para actualizar.");
            }

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }

    public void eliminarAnalisis(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (analisisService.eliminarAnalisis(id)) {
            ctx.status(204).result("Análisis eliminado exitosamente.");
        } else {
            ctx.status(404).result("Análisis con ID " + id + " no encontrado o no se pudo eliminar.");
        }
    }
}