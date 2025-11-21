package Service;

import Model.Parto;
import Repository.PartoRepository;
import java.util.List;
import java.util.Optional;

public class PartoService {

    private final PartoRepository partoRepository;

    public PartoService(PartoRepository partoRepository) {
        this.partoRepository = partoRepository;
    }

    public Parto crearParto(Parto parto) {
        if (parto.getFecha() == null) {
            throw new IllegalArgumentException("La fecha del parto es obligatoria.");
        }

        if (parto.getCantidadCrias() <= 0) {
            throw new IllegalArgumentException("La cantidad de crías debe ser mayor a cero.");
        }

        partoRepository.crear(parto);
        return parto;
    }

    public List<Parto> obtenerTodos() {
        return partoRepository.obtenerTodos();
    }

    public Optional<Parto> buscarPorId(int id) {
        Parto parto = partoRepository.obtenerPorId(id);
        return Optional.ofNullable(parto);
    }

    public Parto actualizarParto(Parto partoActualizado) {
        if (partoRepository.obtenerPorId(partoActualizado.getIdParto()) == null) {
            return null;
        }

        if (partoActualizado.getCantidadCrias() <= 0) {
            throw new IllegalArgumentException("La cantidad de crías debe ser mayor a cero.");
        }

        partoRepository.actualizar(partoActualizado);
        return partoActualizado;
    }

    public boolean eliminarParto(int id) {
        return partoRepository.eliminar(id);
    }
}