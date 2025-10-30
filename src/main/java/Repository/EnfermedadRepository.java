package Repository;

import Model.Enfermedad;

import java.util.ArrayList;
import java.util.List;

public class EnfermedadRepository {
    private List<Enfermedad> enfermedades;

    public EnfermedadRepository() {
        this.enfermedades = new ArrayList<>();
    }

    public Enfermedad crear(Enfermedad enfermedad) {
        enfermedades.add(enfermedad);
        return enfermedad;
    }

    public Enfermedad buscarPorId(int idEnfermedad) {
        for (Enfermedad enfermedad : enfermedades) {
            if (enfermedad.getIdEnfermedad() == idEnfermedad) {
                return enfermedad;
            }
        }
        return null;
    }

    public List<Enfermedad> obtenerTodos() {
        return new ArrayList<>(enfermedades);
    }

    public Enfermedad actualizar(Enfermedad enfermedadActualizada) {
        for (int i = 0; i < enfermedades.size(); i++) {
            if (enfermedades.get(i).getIdEnfermedad() == enfermedadActualizada.getIdEnfermedad()) {
                enfermedades.set(i, enfermedadActualizada);
                return enfermedadActualizada;
            }
        }
        return null;
    }

    public boolean eliminar(int idEnfermedad) {
        for (int i = 0; i < enfermedades.size(); i++) {
            if (enfermedades.get(i).getIdEnfermedad() == idEnfermedad) {
                enfermedades.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Enfermedad> buscarPorNombre(String nombre) {
        List<Enfermedad> resultado = new ArrayList<>();
        for (Enfermedad enfermedad : enfermedades) {
            if (enfermedad.getNombreEnfermedad().toLowerCase().contains(nombre.toLowerCase())) {
                resultado.add(enfermedad);
            }
        }
        return resultado;
    }

    public Enfermedad buscarPorNombreExacto(String nombre) {
        for (Enfermedad enfermedad : enfermedades) {
            if (enfermedad.getNombreEnfermedad().equalsIgnoreCase(nombre)) {
                return enfermedad;
            }
        }
        return null;
    }

    public int obtenerTotal() {
        return enfermedades.size();
    }
}
