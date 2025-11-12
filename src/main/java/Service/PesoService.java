 package Service;

import Model.Peso;
import Repository.PesoRepository;
import java.util.List;
import java.util.Optional;

public class PesoService {

    private final PesoRepository pesoRepository;

    public PesoService(PesoRepository pesoRepository) {
        this.pesoRepository = pesoRepository;
    }

    public Peso crearPeso(Peso peso) {
        if (peso.getPesoActual() <= 0) {
            throw new IllegalArgumentException("El peso actual debe ser mayor a cero.");
        }

        return pesoRepository.crear(peso);
    }

    public List<Peso> obtenerTodos() {
        return pesoRepository.obtenerTodos();
    }

    public Optional<Peso> buscarPorId(int id) {
        Peso peso = pesoRepository.obtenerPorId(id);
        return Optional.ofNullable(peso);
    }

    public Peso actualizarPeso(Peso pesoActualizado) {
        if (pesoRepository.obtenerPorId(pesoActualizado.getIdPeso()) == null) {
            return null;
        }

        if (pesoActualizado.getPesoActual() <= 0) {
            throw new IllegalArgumentException("El peso actual debe ser mayor a cero.");
        }


        return pesoRepository.actualizar(pesoActualizado);
    }

    public boolean eliminarPeso(int id) {
        return pesoRepository.eliminar(id);
    }


}