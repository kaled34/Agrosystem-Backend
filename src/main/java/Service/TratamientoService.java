package Service;

import Model.Tratamiento;
import Repository.TratamientoRepository;
import java.util.List;
import java.util.Optional;

public class TratamientoService {

    private final TratamientoRepository tratamientoRepository;

    public TratamientoService(TratamientoRepository tratamientoRepository) {
        this.tratamientoRepository = tratamientoRepository;
    }

    public Tratamiento crearTratamiento(Tratamiento tratamiento) {
        if (tratamiento.getNombreTratamiento() == null || tratamiento.getNombreTratamiento().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tratamiento es obligatorio.");
        }

        if (tratamiento.getFechaInicio() == null) {
            throw new IllegalArgumentException("La fecha de inicio del tratamiento es obligatoria.");
        }

        if (tratamiento.getFechaFinal() != null && tratamiento.getFechaInicio().isAfter(tratamiento.getFechaFinal())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha final.");
        }

        return tratamientoRepository.crear(tratamiento);
    }

    public List<Tratamiento> obtenerTodos() {
        return tratamientoRepository.obtenerTodos();
    }

    public Optional<Tratamiento> buscarPorId(int id) {
        Tratamiento tratamiento = tratamientoRepository.obtenerPorId(id);
        return Optional.ofNullable(tratamiento);
    }

    public Tratamiento actualizarTratamiento(Tratamiento tratamientoActualizado) {
        if (tratamientoRepository.obtenerPorId(tratamientoActualizado.getIdTratamiento()) == null) {
            return null;
        }

        if (tratamientoActualizado.getNombreTratamiento() == null || tratamientoActualizado.getNombreTratamiento().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tratamiento es obligatorio.");
        }

        if (tratamientoActualizado.getFechaFinal() != null && tratamientoActualizado.getFechaInicio().isAfter(tratamientoActualizado.getFechaFinal())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha final.");
        }

        return tratamientoRepository.actualizar(tratamientoActualizado);
    }

    public boolean eliminarTratamiento(int id) {
        return tratamientoRepository.eliminar(id);
    }
}