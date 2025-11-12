package Service;

import Model.TarjetaSalud;
import Repository.TarjetaSaludRepository;
import java.util.List;
import java.util.Optional;

public class TarjetaSaludService {

    private final TarjetaSaludRepository tarjetaSaludRepository;

    public TarjetaSaludService(TarjetaSaludRepository tarjetaSaludRepository) {
        this.tarjetaSaludRepository = tarjetaSaludRepository;
    }

    public TarjetaSalud crearTarjeta(TarjetaSalud tarjeta) {
        if (tarjeta.getIdAnimal().idAnimal <= 0) {
            throw new IllegalArgumentException("El ID del animal es obligatorio y debe ser válido.");
        }

        return tarjetaSaludRepository.crear(tarjeta);
    }

    public List<TarjetaSalud> obtenerTodas() {
        return tarjetaSaludRepository.obtenerTodas();
    }

    public Optional<TarjetaSalud> buscarPorId(int id) {
        TarjetaSalud tarjeta = tarjetaSaludRepository.obtenerPorId(id);
        return Optional.ofNullable(tarjeta);
    }

    public TarjetaSalud actualizarTarjeta(TarjetaSalud tarjetaActualizada) {
        if (tarjetaSaludRepository.obtenerPorId(tarjetaActualizada.getIdTarjeta()) == null) {
            return null;
        }

        return tarjetaSaludRepository.actualizar(tarjetaActualizada);
    }

    public boolean eliminarTarjeta(int id) {
        return tarjetaSaludRepository.eliminar(id);
    }

    public Optional<TarjetaSalud> buscarPorAnimal(int idAnimal) {
        TarjetaSalud tarjeta = tarjetaSaludRepository.obtenerPorId(idAnimal);
        return Optional.ofNullable(tarjeta);
    }
}