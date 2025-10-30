package Controller;

import io.javalin.http.Context;
import Model.Peso;
import Service.PesoService;
import java.util.List;
import java.util.Optional;

public class PesoController {

    private final PesoService pesoService;

    public PesoController(PesoService pesoService) {
        this.pesoService = pesoService;
    }

    public void obtenerTodosPesos(Context ctx) {
        List<Peso> pesos = pesoService.obtenerTodos();

        if (pesos.isEmpty()) {
            ctx.status(204).result("No hay registros de peso.");
        } else {
            ctx.status(200).json(pesos);
        }
    }

    public void buscarPesoPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<Peso> peso = pesoService.buscarPorId(id);

        if (peso.isPresent()) {
            ctx.status(200).json(peso.get());
        } else {
            ctx.status(404).result("Registro de peso con ID " + id + " no encontrado.");
        }
    }

    public void crearPeso(Context ctx) {
        try {
            Peso nuevoPeso = ctx.bodyAsClass(Peso.class);

            Peso pesoCreado = pesoService.crearPeso(nuevoPeso);

            ctx.status(201).json(pesoCreado);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno del servidor al procesar la solicitud: " + e.getMessage());
        }
    }

    public void actualizarPeso(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Peso pesoActualizado = ctx.bodyAsClass(Peso.class);

            if (pesoActualizado.getIdPeso() != id) {
                ctx.status(400).result("El ID del cuerpo no coincide con el ID de la ruta.");
                return;
            }

            Peso resultado = pesoService.actualizarPeso(pesoActualizado);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {
                ctx.status(404).result("Registro de peso con ID " + id + " no encontrado para actualizar.");
            }

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }

    public void eliminarPeso(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (pesoService.eliminarPeso(id)) {
            ctx.status(204).result("Registro de peso eliminado exitosamente.");
        } else {
            ctx.status(404).result("Registro de peso con ID " + id + " no encontrado o no se pudo eliminar.");
        }
    }

}
