package Repository;

import Model.Medicamento;
import Model.Animales;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoRepository {
    private List<Medicamento> medicamentos;

    public MedicamentoRepository() {
        this.medicamentos = new ArrayList<>();
    }

    public Medicamento crear(Medicamento medicamento) {
        medicamentos.add(medicamento);
        return medicamento;
    }

    public Medicamento buscarPorId(int idMedicamento) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getIdMedicamento() == idMedicamento) {
                return medicamento;
            }
        }
        return null;
    }

    public List<Medicamento> obtenerTodos() {
        return new ArrayList<>(medicamentos);
    }

    public Medicamento actualizar(Medicamento medicamentoActualizado) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getIdMedicamento() == medicamentoActualizado.getIdMedicamento()) {
                medicamentos.set(i, medicamentoActualizado);
                return medicamentoActualizado;
            }
        }
        return null;
    }

    public boolean eliminar(int idMedicamento) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getIdMedicamento() == idMedicamento) {
                medicamentos.remove(i);
                return true;
            }
        }
        return false;
    }



    public List<Medicamento> buscarPorNombre(String nombre) {
        List<Medicamento> resultado = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getNombreMedicamento().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(medicamento);
            }
        }
        return resultado;
    }

    public int obtenerTotal() {
        return medicamentos.size();
    }
}