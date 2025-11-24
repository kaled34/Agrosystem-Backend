package Controller;

import java.util.List;
import java.util.Optional;

import Model.Tratamiento;
import Service.TratamientoService;
import io.javalin.http.Context;

public class TratamientoController {

    private final TratamientoService tratamientoService;

    public TratamientoController(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    public void obtenerTodosTratamientos(Context ctx) {
        List<Tratamiento> tratamientos = tratamientoService.obtenerTodos();

        if (tratamientos.isEmpty()) {
            ctx.status(204).result("No hay tratamientos registrados.");
        } else {
            ctx.status(200).json(tratamientos);
        }
    }

    public void buscarTratamientoPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<Tratamiento> tratamiento = tratamientoService.buscarPorId(id);

        if (tratamiento.isPresent()) {
            ctx.status(200).json(tratamiento.get());
        } else {
            ctx.status(404).result("Tratamiento con ID " + id + " no encontrado.");
        }
    }

    public void crearTratamiento(Context ctx) {
        try {
            Tratamiento nuevoTratamiento = ctx.bodyAsClass(Tratamiento.class);

            Tratamiento tratamientoCreado = tratamientoService.crearTratamiento(nuevoTratamiento);

            if (tratamientoCreado != null) {
                ctx.status(201).json(tratamientoCreado);
            } else {
                ctx.status(500).result("No se pudo crear el tratamiento.");
            }

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno del servidor al procesar la solicitud: " + e.getMessage());
        }
    }

    public void actualizarTratamiento(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Tratamiento tratamientoActualizado = ctx.bodyAsClass(Tratamiento.class);

            if (tratamientoActualizado.getIdTratamiento() != id) {
                ctx.status(400).result("El ID del cuerpo no coincide con el ID de la ruta.");
                return;
            }

            Tratamiento resultado = tratamientoService.actualizarTratamiento(tratamientoActualizado);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {
                ctx.status(404).result("Tratamiento con ID " + id + " no encontrado para actualizar.");
            }

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }

    public void eliminarTratamiento(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (tratamientoService.eliminarTratamiento(id)) {
            ctx.status(204).result("Tratamiento eliminado exitosamente.");
        } else {
            ctx.status(404).result("Tratamiento con ID " + id + " no encontrado o no se pudo eliminar.");
        }
    }
}