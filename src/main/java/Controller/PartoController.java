package Controller;

import io.javalin.http.Context;
import Model.Parto;
import Model.Animales;
import Service.PartoService;
import Repository.AnimalesRepository;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.time.LocalDate;

public class PartoController {

    private final PartoService partoService;

    public PartoController(PartoService partoService) {
        this.partoService = partoService;
    }

    public void obtenerTodosPartos(Context ctx) {
        List<Parto> partos = partoService.obtenerTodos();

        if (partos.isEmpty()) {
            ctx.status(204).result("No hay partos registrados.");
        } else {
            ctx.status(200).json(partos);
        }
    }

    public void buscarPartoPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<Parto> parto = partoService.buscarPorId(id);

        if (parto.isPresent()) {
            ctx.status(200).json(parto.get());
        } else {
            ctx.status(404).result("Parto con ID " + id + " no encontrado.");
        }
    }

    public void crearParto(Context ctx) {
        try {
            Map<String, Object> body = ctx.bodyAsClass(Map.class);

            Map<String, Object> madreData = (Map<String, Object>) body.get("idMadre");
            int idMadre = ((Number) madreData.get("idAnimal")).intValue();

            AnimalesRepository animalRepo = new AnimalesRepository();
            Animales madre = animalRepo.obtenerPorId(idMadre);

            if (madre == null) {
                ctx.status(400).result("Animal madre no encontrado.");
                return;
            }

            Parto nuevoParto = new Parto();
            nuevoParto.idMadre = madre;
            nuevoParto.fecha = LocalDate.parse((String) body.get("fecha"));
            nuevoParto.cantidadCrias = ((Number) body.get("cantidadCrias")).intValue();

            Parto partoCreado = partoService.crearParto(nuevoParto);

            ctx.status(201).json(partoCreado);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno del servidor al procesar la solicitud: " + e.getMessage());
        }
    }

    public void actualizarParto(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Map<String, Object> body = ctx.bodyAsClass(Map.class);

            Optional<Parto> partoExistente = partoService.buscarPorId(id);

            if (!partoExistente.isPresent()) {
                ctx.status(404).result("Parto con ID " + id + " no encontrado para actualizar.");
                return;
            }

            Parto partoActualizado = partoExistente.get();
            partoActualizado.fecha = LocalDate.parse((String) body.get("fecha"));
            partoActualizado.cantidadCrias = ((Number) body.get("cantidadCrias")).intValue();

            Parto resultado = partoService.actualizarParto(partoActualizado);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {
                ctx.status(404).result("Parto con ID " + id + " no encontrado para actualizar.");
            }

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }

    public void eliminarParto(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (partoService.eliminarParto(id)) {
            ctx.status(204).result("Parto eliminado exitosamente.");
        } else {
            ctx.status(404).result("Parto con ID " + id + " no encontrado o no se pudo eliminar.");
        }
    }
}