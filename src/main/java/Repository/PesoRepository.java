package Repository;
import Model.Peso;

import java.util.ArrayList;
import java.util.List;

public class PesoRepository {
    private List<Peso> pesos;

    public PesoRepository() {
        this.pesos = new ArrayList<>();
    }

    public Peso crear(Peso peso) {
        pesos.add(peso);
        return peso;
    }

    public Peso buscarPorId(int idPeso) {
        for (Peso peso : pesos) {
            if (peso.getIdPeso() == idPeso) {
                return peso;
            }
        }
        return null;
    }

    public List<Peso> obtenerTodos() {
        return new ArrayList<>(pesos);
    }

    public Peso actualizar(Peso pesoActualizado) {
        for (int i = 0; i < pesos.size(); i++) {
            if (pesos.get(i).getIdPeso() == pesoActualizado.getIdPeso()) {
                pesos.set(i, pesoActualizado);
                return pesoActualizado;
            }
        }
        return null;
    }

    public boolean eliminar(int idPeso) {
        for (int i = 0; i < pesos.size(); i++) {
            if (pesos.get(i).getIdPeso() == idPeso) {
                pesos.remove(i);
                return true;
            }
        }
        return false;
    }


    public int obtenerTotal() {
        return pesos.size();
    }
}