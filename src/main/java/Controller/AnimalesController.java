package Controller;

import io.javalin.http.Context;
import Model.Animales;
import Service.AnimalesService;
import java.util.List;
import java.util.Optional;


public class AnimalesController {

    private final AnimalesService animalesService;


    public AnimalesController(AnimalesService animalesService) {
        this.animalesService = animalesService;
    }

    public void obtenerTodosAnimales(Context ctx) {
        List<Animales> animales = animalesService.obtenerTodos();

        if (animales.isEmpty()) {
            ctx.status(204).result("No hay animales registrados.");
        } else {
            ctx.status(200).json(animales);
        }
    }

    public void buscarAnimalPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<Animales> animal = animalesService.buscarPorId(id);

        if (animal.isPresent()) {
            ctx.status(200).json(animal.get());
        } else {
            ctx.status(404).result("Animal con ID " + id + " no encontrado.");
        }
    }

    public void crearAnimal(Context ctx) {
        try {
            Animales nuevoAnimal = ctx.bodyAsClass(Animales.class);

            Animales animalCreado = animalesService.crearAnimal(nuevoAnimal);

            ctx.status(201).json(animalCreado);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error al procesar la solicitud: " + e.getMessage());
        }
    }

    public void actualizarAnimal(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {

            Animales animalActualizado = ctx.bodyAsClass(Animales.class);


            if (animalActualizado.getIdAnimal() != id) {
                ctx.status(400).result("El ID del cuerpo no coincide con el ID de la ruta.");
                return;
            }


            Animales resultado = animalesService.actualizarAnimal(animalActualizado);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {

                ctx.status(404).result("Animal con ID " + id + " no encontrado para actualizar.");
            }

        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }


    public void eliminarAnimal(Context ctx) {

        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (animalesService.eliminarAnimal(id)) {

            ctx.status(204).result("Animal eliminado exitosamente.");
        } else {

            ctx.status(404).result("Animal con ID " + id + " no encontrado o no se pudo eliminar.");
        }
    }
}