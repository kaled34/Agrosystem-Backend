package Service;

import Model.Animales;
import Repository.AnimalesRepository;
import java.util.List;
import java.util.Optional;

public class AnimalesService {

    private final AnimalesRepository animalesRepository;

    public AnimalesService(AnimalesRepository animalesRepository) {
        this.animalesRepository = animalesRepository;
    }

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

    public List<Animales> obtenerTodos() {
        return animalesRepository.obtenerTodos();
    }

    public Optional<Animales> buscarPorId(int id) {
        Animales animal = animalesRepository.obtenerPorId(id);
        return Optional.ofNullable(animal);
    }

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

    public boolean eliminarAnimal(int id) {
        return animalesRepository.eliminar(id);
    }
}