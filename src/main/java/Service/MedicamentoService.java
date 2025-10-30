package Service;

import Model.Medicamento;
import Repository.MedicamentoRepository;
import java.util.List;
import java.util.Optional;


public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }


    public Medicamento crearMedicamento(Medicamento medicamento) {

        if (medicamento.getNombreMedicamento() == null || medicamento.getNombreMedicamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del medicamento es obligatorio.");
        }


        if (medicamento.getPrincipioActivo() == null || medicamento.getPrincipioActivo().trim().isEmpty()) {
            throw new IllegalArgumentException("El principio activo es obligatorio.");
        }


        if (medicamento.getCantidadMedicamento() <= 0) {
            throw new IllegalArgumentException("La cantidad del medicamento debe ser mayor a cero.");
        }


        return medicamentoRepository.crear(medicamento);
    }


    public List<Medicamento> obtenerTodos() {
        return medicamentoRepository.obtenerTodos();
    }


    public Optional<Medicamento> buscarPorId(int id) {
        Medicamento medicamento = medicamentoRepository.buscarPorId(id);
        return Optional.ofNullable(medicamento);
    }


    public Medicamento actualizarMedicamento(Medicamento medicamentoActualizado) {

        if (medicamentoRepository.buscarPorId(medicamentoActualizado.getIdMedicamento()) == null) {
            return null; // El medicamento no existe
        }


        if (medicamentoActualizado.getNombreMedicamento() == null || medicamentoActualizado.getNombreMedicamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del medicamento es obligatorio para la actualización.");
        }

        return medicamentoRepository.actualizar(medicamentoActualizado);
    }


    public boolean eliminarMedicamento(int id) {
        return medicamentoRepository.eliminar(id);
    }
}
