package Controller;

import io.javalin.http.Context;
import Model.Enfermedad;
import Service.EnfermedadService;
import java.util.List;
import java.util.Optional;

public class EnfermedadController {

    private final EnfermedadService enfermedadService;

    public EnfermedadController(EnfermedadService enfermedadService) {
        this.enfermedadService = enfermedadService;
    }

    public void obtenerTodasEnfermedades(Context ctx) {
        List<Enfermedad> enfermedades = enfermedadService.obtenerTodas();

        if (enfermedades.isEmpty()) {
            ctx.status(204).result("No hay enfermedades registradas.");
        } else {
            ctx.status(200).json(enfermedades);
        }
    }

    public void buscarEnfermedadPorId(Context ctx) {

        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<Enfermedad> enfermedad = enfermedadService.buscarPorId(id);

        if (enfermedad.isPresent()) {
            ctx.status(200).json(enfermedad.get());
        } else {
            ctx.status(404).result("Enfermedad con ID " + id + " no encontrada.");
        }
    }


    public void crearEnfermedad(Context ctx) {
        try {

            Enfermedad nuevaEnfermedad = ctx.bodyAsClass(Enfermedad.class);


            Enfermedad enfermedadCreada = enfermedadService.crearEnfermedad(nuevaEnfermedad);


            ctx.status(201).json(enfermedadCreada);

        } catch (IllegalArgumentException e) {

            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {

            ctx.status(500).result("Error interno del servidor al procesar la solicitud: " + e.getMessage());
        }
    }


    public void actualizarEnfermedad(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Enfermedad enfermedadActualizada = ctx.bodyAsClass(Enfermedad.class);


            if (enfermedadActualizada.getIdEnfermedad() != id) {
                ctx.status(400).result("El ID del cuerpo (" + enfermedadActualizada.getIdEnfermedad() + ") no coincide con el ID de la ruta (" + id + ").");
                return;
            }

            Enfermedad resultado = enfermedadService.actualizarEnfermedad(enfermedadActualizada);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {

                ctx.status(404).result("Enfermedad con ID " + id + " no encontrada para actualizar.");
            }

        } catch (IllegalArgumentException e) {

            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }


    public void eliminarEnfermedad(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (enfermedadService.eliminarEnfermedad(id)) {

            ctx.status(204).result("Enfermedad eliminada exitosamente.");
        } else {

            ctx.status(404).result("Enfermedad con ID " + id + " no encontrada o no se pudo eliminar.");
        }
    }
}