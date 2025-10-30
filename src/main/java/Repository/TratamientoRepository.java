package Repository;

import Model.Animales;
import Model.Tratamiento;

import java.util.ArrayList;
import java.util.List;

public class TratamientoRepository {
    private List<Tratamiento> tratamientos;

    public TratamientoRepository() {
        this.tratamientos = new ArrayList<>();
    }

    public Tratamiento crear(Tratamiento tratamiento) {
        tratamientos.add(tratamiento);
        return tratamiento;
    }

    public Tratamiento buscarPorId(int idTratamiento) {
        for (Tratamiento tratamiento : tratamientos) {
            if (tratamiento.getIdTratamiento() == idTratamiento) {
                return tratamiento;
            }
        }
        return null;
    }

    public List<Tratamiento> obtenerTodos() {
        return new ArrayList<>(tratamientos);
    }

    public Tratamiento actualizar(Tratamiento tratamientoActualizado) {
        for (int i = 0; i < tratamientos.size(); i++) {
            if (tratamientos.get(i).getIdTratamiento() == tratamientoActualizado.getIdTratamiento()) {
                tratamientos.set(i, tratamientoActualizado);
                return tratamientoActualizado;
            }
        }
        return null;
    }

    public boolean eliminar(int idTratamiento) {
        for (int i = 0; i < tratamientos.size(); i++) {
            if (tratamientos.get(i).getIdTratamiento() == idTratamiento) {
                tratamientos.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Tratamiento> buscarPorAnimal(Animales animal) {
        List<Tratamiento> resultado = new ArrayList<>();
        for (Tratamiento tratamiento : tratamientos) {
            if (tratamiento.getIdAnimales().getIdAnimal() == animal.getIdAnimal()) {
                resultado.add(tratamiento);
            }
        }
        return resultado;
    }

    public List<Tratamiento> buscarPorVeterinario(String nombreVeterinario) {
        List<Tratamiento> resultado = new ArrayList<>();
        for (Tratamiento tratamiento : tratamientos) {
            if (tratamiento.getNombreVeterinario().equalsIgnoreCase(nombreVeterinario)) {
                resultado.add(tratamiento);
            }
        }
        return resultado;
    }

    public int obtenerTotal() {
        return tratamientos.size();
    }
}