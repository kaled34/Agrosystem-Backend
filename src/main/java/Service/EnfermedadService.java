package Service;

import Model.Enfermedad;
import Repository.EnfermedadRepository;
import java.util.List;
import java.util.Optional;

public class EnfermedadService {

    private final EnfermedadRepository enfermedadRepository;

    public EnfermedadService(EnfermedadRepository enfermedadRepository) {
        this.enfermedadRepository = enfermedadRepository;
    }

    public Enfermedad crearEnfermedad(Enfermedad enfermedad) {
        if (enfermedad.getNombreEnfermedad() == null || enfermedad.getNombreEnfermedad().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la enfermedad es obligatorio.");
        }

        if (enfermedadRepository.buscarPorNombreExacto(enfermedad.getNombreEnfermedad()) != null) {
            throw new IllegalArgumentException("Ya existe una enfermedad con el nombre: " + enfermedad.getNombreEnfermedad());
        }

        if (enfermedad.getDiasDuracion() < 0) {
            throw new IllegalArgumentException("Los días de duración no pueden ser negativos.");
        }

        return enfermedadRepository.crear(enfermedad);
    }

    public List<Enfermedad> obtenerTodas() {
        return enfermedadRepository.obtenerTodos();
    }


    public Optional<Enfermedad> buscarPorId(int id) {
        Enfermedad enfermedad = enfermedadRepository.buscarPorId(id);
        return Optional.ofNullable(enfermedad);
    }

    public Enfermedad actualizarEnfermedad(Enfermedad enfermedadActualizada) {
        if (enfermedadRepository.buscarPorId(enfermedadActualizada.getIdEnfermedad()) == null) {
            return null;
        }


        if (enfermedadActualizada.getNombreEnfermedad() == null || enfermedadActualizada.getNombreEnfermedad().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la enfermedad es obligatorio.");
        }


        return enfermedadRepository.actualizar(enfermedadActualizada);
    }

    public boolean eliminarEnfermedad(int id) {
        return enfermedadRepository.eliminar(id);
    }
}