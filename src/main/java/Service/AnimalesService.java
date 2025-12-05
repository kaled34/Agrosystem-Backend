package Service;

import Model.Animales;
import Repository.AnimalesRepository;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar la lógica de negocio relacionada con animales.
 * Esta clase actúa como capa intermedia entre los controladores y el
 * repositorio,
 * aplicando validaciones y reglas de negocio antes de las operaciones de base
 * de datos.
 * 
 * @author Agrosystem Team
 * @version 1.0
 */
public class AnimalesService {

    private final AnimalesRepository animalesRepository;

    /**
     * Constructor que inyecta el repositorio de animales.
     * 
     * @param animalesRepository Repositorio para acceso a datos de animales
     */
    public AnimalesService(AnimalesRepository animalesRepository) {
        this.animalesRepository = animalesRepository;
    }

    /**
     * Crea un nuevo animal en el sistema.
     * Valida que el nombre no sea nulo y que no exista duplicado por nombre o
     * arete.
     * 
     * @param animal Objeto Animal a crear
     * @return El animal creado
     * @throws IllegalArgumentException si el nombre es nulo, o si ya existe un
     *                                  animal
     *                                  con el mismo nombre o número de arete
     * 
     * @example
     * 
     *          <pre>
     *          Animales nuevoAnimal = new Animales();
     *          nuevoAnimal.setNombreAnimal("Vaca Lola");
     *          nuevoAnimal.setNumArete(12345);
     *          Animales creado = animalesService.crearAnimal(nuevoAnimal);
     *          </pre>
     */
    public Animales crearAnimal(Animales animal) {
        if (animal.getNombreAnimal() == null) {
            throw new IllegalArgumentException("El nombre del animal no puede ser nulo.");
        }

        if (animalesRepository.existePorNombre(animal.getNombreAnimal())) {
            throw new IllegalArgumentException("Ya existe un animal con ese nombre.");
        }

        if (animalesRepository.existePorNumArete(animal.getNumArete())) {
            throw new IllegalArgumentException("Ya existe un animal con ese número de arete.");
        }

        animalesRepository.crear(animal);
        return animal;
    }

    /**
     * Obtiene la lista de todos los animales registrados en el sistema.
     * 
     * @return Lista de todos los animales
     */
    public List<Animales> obtenerTodos() {
        return animalesRepository.obtenerTodos();
    }

    /**
     * Busca un animal por su ID.
     * 
     * @param id ID del animal a buscar
     * @return Optional conteniendo el animal si existe, Optional.empty() si no
     */
    public Optional<Animales> buscarPorId(int id) {
        Animales animal = animalesRepository.obtenerPorId(id);
        return Optional.ofNullable(animal);
    }

    /**
     * Actualiza la información de un animal existente.
     * Valida que el animal exista y que no haya conflictos con otros registros.
     * 
     * @param animalActualizado Animal con la información actualizada
     * @return El animal actualizado, o null si el animal no existe
     * @throws IllegalArgumentException si ya existe otro animal con el mismo nombre
     *                                  o arete
     */
    public Animales actualizarAnimal(Animales animalActualizado) {
        if (animalesRepository.obtenerPorId(animalActualizado.getIdAnimal()) == null) {
            return null;
        }

        Animales existentePorNombre = animalesRepository.obtenerPorNombre(animalActualizado.getNombreAnimal());
        if (existentePorNombre != null && existentePorNombre.getIdAnimal() != animalActualizado.getIdAnimal()) {
            throw new IllegalArgumentException("Ya existe otro animal con ese nombre.");
        }

        Animales existentePorArete = animalesRepository.obtenerPorNumArete(animalActualizado.getNumArete());
        if (existentePorArete != null && existentePorArete.getIdAnimal() != animalActualizado.getIdAnimal()) {
            throw new IllegalArgumentException("Ya existe otro animal con ese número de arete.");
        }

        animalesRepository.actualizar(animalActualizado);
        return animalActualizado;
    }

    /**
     * Elimina un animal del sistema por su ID.
     * 
     * @param id ID del animal a eliminar
     * @return true si el animal fue eliminado exitosamente, false si no existe
     */
    public boolean eliminarAnimal(int id) {
        return animalesRepository.eliminar(id);
    }
}