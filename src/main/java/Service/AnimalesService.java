package Service;

import java.util.List;
import java.util.Optional;

import Model.Animales;
import Repository.AnimalesRepository;

public class AnimalesService {

    private final AnimalesRepository animalesRepository;

    public AnimalesService(AnimalesRepository animalesRepository) {
        this.animalesRepository = animalesRepository;
    }

    public Animales crearAnimal(Animales animal) {
        if (animal.getNombreAnimal() == null) {
            throw new IllegalArgumentException("El nombre del animal no puede ser nulo.");
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
        Animales resultado = animalesRepository.actualizar(animalActualizado);
        return resultado;
    }

    public boolean eliminarAnimal(int id) {
        return animalesRepository.eliminar(id);
    }
}
