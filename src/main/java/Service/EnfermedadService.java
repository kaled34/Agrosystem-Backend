package Service;

import java.util.List;
import java.util.Optional;

import Model.Enfermedad;
import Repository.AnalisisRepository;
import Repository.EnfermedadRepository;
import Repository.MedicamentoRepository;

public class EnfermedadService {

    private final EnfermedadRepository enfermedadRepository;

    public EnfermedadService(EnfermedadRepository enfermedadRepository) {
        this.enfermedadRepository = enfermedadRepository;
    }

    public Enfermedad crearEnfermedad(Enfermedad enfermedad) {
        if (enfermedad.getNombreEnfermedad() == null || enfermedad.getNombreEnfermedad().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la enfermedad es obligatorio.");
        }

        if (enfermedadRepository.obtenerPorNombre(enfermedad.getNombreEnfermedad()) != null) {
            throw new IllegalArgumentException("Ya existe una enfermedad con el nombre: " + enfermedad.getNombreEnfermedad());
        }

        if (enfermedad.duracionEstimada < 0) {
            throw new IllegalArgumentException("Los días de duración no pueden ser negativos.");
        }

        // Validate referenced entities exist to avoid FK constraint errors
        AnalisisRepository analisisRepo = new AnalisisRepository();
        if (enfermedad.getIdAnalisis() != null) {
            if (analisisRepo.obtenerPorId(enfermedad.getIdAnalisis().getIdAnalisis()) == null) {
                throw new IllegalArgumentException("Analisis no encontrado con id: " + enfermedad.getIdAnalisis().getIdAnalisis());
            }
        }

        MedicamentoRepository medicamentoRepo = new MedicamentoRepository();
        if (enfermedad.getIdMedicamento() != null) {
            if (medicamentoRepo.obtenerPorId(enfermedad.getIdMedicamento().getIdMedicamento()) == null) {
                throw new IllegalArgumentException("Medicamento no encontrado con id: " + enfermedad.getIdMedicamento().getIdMedicamento());
            }
        }

        return enfermedadRepository.crear(enfermedad);
    }

    public List<Enfermedad> obtenerTodas() {
        return enfermedadRepository.obtenerTodas();
    }

    public Optional<Enfermedad> buscarPorId(int id) {
        Enfermedad enfermedad = enfermedadRepository.obtenerPorId(id);
        return Optional.ofNullable(enfermedad);
    }

    public Enfermedad actualizarEnfermedad(Enfermedad enfermedadActualizada) {
        if (enfermedadRepository.obtenerPorId(enfermedadActualizada.getIdEnfermedad()) == null) {
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