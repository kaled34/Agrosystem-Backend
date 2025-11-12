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
            throw new IllegalArgumentException("El peso actual debe ser positivo.");
        }

        return animalesRepository.crear(animal);
    }

    public List<Animales> obtenerTodos() {
        return animalesRepository.obtenerTodos();
    }

    public Optional<Animales> buscarPorId(int id) {
        Animales animal = animalesRepository.buscarPorId(id);
        return Optional.ofNullable(animal);
    }

    public Animales actualizarAnimal(Animales animalActualizado) {
        if (animalesRepository.buscarPorId(animalActualizado.getIdAnimal()) == null) {
            return null;
        }
        return animalesRepository.actualizar(animalActualizado);
    }
    public boolean eliminarAnimal(int id) {
        return animalesRepository.eliminar(id);
    }
}

