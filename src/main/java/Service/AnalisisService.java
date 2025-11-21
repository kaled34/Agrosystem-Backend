package Service;

import Model.Analisis;
import Repository.AnalisisRepository;
import java.util.List;
import java.util.Optional;

public class AnalisisService {

    private final AnalisisRepository analisisRepository;

    public AnalisisService(AnalisisRepository analisisRepository) {
        this.analisisRepository = analisisRepository;
    }

    public Analisis crearAnalisis(Analisis analisis) {
        if (analisis.getTipoAnalisis() == null || analisis.getTipoAnalisis().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de análisis es obligatorio.");
        }

        analisisRepository.crear(analisis);
        return analisis;
    }

    public List<Analisis> obtenerTodos() {
        return analisisRepository.obtenerTodos();
    }

    public Optional<Analisis> buscarPorId(int id) {
        Analisis analisis = analisisRepository.obtenerPorId(id);
        return Optional.ofNullable(analisis);
    }

    public Analisis actualizarAnalisis(Analisis analisisActualizado) {
        if (analisisRepository.obtenerPorId(analisisActualizado.getIdAnalisis()) == null) {
            return null;
        }

        if (analisisActualizado.getTipoAnalisis() == null || analisisActualizado.getTipoAnalisis().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de análisis es obligatorio.");
        }

        analisisRepository.actualizar(analisisActualizado);
        return analisisActualizado;
    }

    public boolean eliminarAnalisis(int id) {
        return analisisRepository.eliminar(id);
    }
}