package Controller;

import java.util.List;
import java.util.Optional;

import Model.Animales;
import Service.AnimalesService;
import io.javalin.http.Context;

/**
 * Controlador REST para gestionar las operaciones HTTP relacionadas con
 * animales.
 * Maneja las peticiones del frontend y coordina con el servicio de animales
 * para ejecutar la lógica de negocio, retornando respuestas JSON.
 * 
 * <p>
 * Endpoints disponibles:
 * </p>
 * <ul>
 * <li>GET /animales - Obtiene todos los animales</li>
 * <li>GET /animales/:id - Obtiene un animal por ID</li>
 * <li>POST /animales - Crea un nuevo animal</li>
 * <li>PUT /animales/:id - Actualiza un animal existente</li>
 * <li>DELETE /animales/:id - Elimina un animal</li>
 * </ul>
 * 
 * @author Agrosystem Team
 * @version 1.0
 */
public class AnimalesController {

    private final AnimalesService animalesService;

    /**
     * Constructor que inyecta el servicio de animales.
     * 
     * @param animalesService Servicio para lógica de negocio de animales
     */
    public AnimalesController(AnimalesService animalesService) {
        this.animalesService = animalesService;
    }

    /**
     * Maneja la petición GET para obtener todos los animales.
     * 
     * @param ctx Contexto de Javalin con información de la petición y respuesta
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Retorna lista de animales en JSON</li>
     *            <li>204 No Content - Si no hay animales registrados</li>
     *            </ul>
     */
    public void obtenerTodosAnimales(Context ctx) {
        List<Animales> animales = animalesService.obtenerTodos();

        if (animales.isEmpty()) {
            ctx.status(204).result("No hay animales registrados.");
        } else {
            ctx.status(200).json(animales);
        }
    }

    /**
     * Maneja la petición GET para buscar un animal por su ID.
     * 
     * @param ctx Contexto de Javalin (path parameter "id" requerido)
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Retorna el animal encontrado en JSON</li>
     *            <li>404 Not Found - Si el animal no existe</li>
     *            </ul>
     */
    public void buscarAnimalPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<Animales> animal = animalesService.buscarPorId(id);

        if (animal.isPresent()) {
            ctx.status(200).json(animal.get());
        } else {
            ctx.status(404).result("Animal con ID: " + id + " no encontrado.");
        }
    }

    /**
     * Maneja la petición POST para crear un nuevo animal.
     * 
     * @param ctx Contexto de Javalin (body JSON con datos del animal requerido)
     * 
     *            <p>
     *            Ejemplo de body JSON:
     *            </p>
     * 
     *            <pre>
     * {
     *   "nombreAnimal": "Vaca Lola",
     *   "numArete": 12345,
     *   "rebaño": "Rebaño A",
     *   "fechaNacimiento": "2023-01-15",
     *   "pesoInicial": 450.5
     * }
     *            </pre>
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>201 Created - Animal creado exitosamente</li>
     *            <li>400 Bad Request - Datos inválidos o duplicados</li>
     *            <li>500 Internal Server Error - Error del servidor</li>
     *            </ul>
     */
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

    /**
     * Maneja la petición PUT para actualizar un animal existente.
     * 
     * @param ctx Contexto de Javalin (path parameter "id" y body JSON requeridos)
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Animal actualizado exitosamente</li>
     *            <li>400 Bad Request - ID del body no coincide con ID de la ruta o
     *            datos inválidos</li>
     *            <li>404 Not Found - Animal no encontrado</li>
     *            </ul>
     */
    public void actualizarAnimal(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {

            Animales animalActualizado = ctx.bodyAsClass(Animales.class);

            // Log para depuración: mostrar campos relevantes recibidos
            System.out.println("[DEBUG] actualizarAnimal - body recibidO: idAnimal=" + animalActualizado.getIdAnimal()
                    + ", idPadre=" + animalActualizado.getIdPadre()
                    + ", idMadre=" + animalActualizado.getIdMadre()
                    + ", nombre=" + animalActualizado.getNombreAnimal());

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

    /**
     * Maneja la petición DELETE para eliminar un animal.
     * 
     * @param ctx Contexto de Javalin (path parameter "id" requerido)
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>204 No Content - Animal eliminado correctamente</li>
     *            <li>404 Not Found - Animal no encontrado o no se pudo
     *            eliminar</li>
     *            </ul>
     */
    public void eliminarAnimal(Context ctx) {

        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (animalesService.eliminarAnimal(id)) {

            ctx.status(204).result("Animal eliminado exitosamente.");
        } else {

            ctx.status(404).result("Animal con ID " + id + " no encontrado o no se pudo eliminar.");
        }
    }
}