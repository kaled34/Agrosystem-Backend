package Controller;

import io.javalin.http.Context;
import Model.TarjetaSalud;
import Service.TarjetaSaludService;
import java.util.List;
import java.util.Optional;

public class TarjetaSaludController {

    private final TarjetaSaludService tarjetaSaludService;

    public TarjetaSaludController(TarjetaSaludService tarjetaSaludService) {
        this.tarjetaSaludService = tarjetaSaludService;
    }

    public void obtenerTodasTarjetas(Context ctx) {
        List<TarjetaSalud> tarjetas = tarjetaSaludService.obtenerTodas();

        if (tarjetas.isEmpty()) {
            ctx.status(204).result("No hay tarjetas de salud registradas.");
        } else {
            ctx.status(200).json(tarjetas);
        }
    }

    public void buscarTarjetaPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<TarjetaSalud> tarjeta = tarjetaSaludService.buscarPorId(id);

        if (tarjeta.isPresent()) {
            ctx.status(200).json(tarjeta.get());
        } else {
            ctx.status(404).result("Tarjeta de salud con ID " + id + " no encontrada.");
        }
    }

    public void crearTarjeta(Context ctx) {
        try {
            TarjetaSalud nuevaTarjeta = ctx.bodyAsClass(TarjetaSalud.class);

            TarjetaSalud tarjetaCreada = tarjetaSaludService.crearTarjeta(nuevaTarjeta);

            ctx.status(201).json(tarjetaCreada);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno del servidor al procesar la solicitud: " + e.getMessage());
        }
    }

    public void actualizarTarjeta(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            TarjetaSalud tarjetaActualizada = ctx.bodyAsClass(TarjetaSalud.class);

            if (tarjetaActualizada.getIdTarjeta() != id) {
                ctx.status(400).result("El ID del cuerpo no coincide con el ID de la ruta.");
                return;
            }

            TarjetaSalud resultado = tarjetaSaludService.actualizarTarjeta(tarjetaActualizada);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {
                ctx.status(404).result("Tarjeta de salud con ID " + id + " no encontrada para actualizar.");
            }

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }

    public void eliminarTarjeta(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (tarjetaSaludService.eliminarTarjeta(id)) {
            ctx.status(204).result("Tarjeta de salud eliminada exitosamente.");
        } else {
            ctx.status(404).result("Tarjeta de salud con ID " + id + " no encontrada o no se pudo eliminar.");
        }
    }

    public void buscarTarjetaPorAnimal(Context ctx) {
        int idAnimal = ctx.queryParamAsClass("idAnimal", Integer.class).get();

        Optional<TarjetaSalud> tarjeta = tarjetaSaludService.buscarPorAnimal(idAnimal);

        if (tarjeta.isPresent()) {
            ctx.status(200).json(tarjeta.get());
        } else {
            ctx.status(404).result("No se encontró tarjeta de salud para el animal con ID: " + idAnimal);
        }
    }
}
