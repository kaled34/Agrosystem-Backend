package Repository;

import Model.Animales;
import java.util.ArrayList;
import java.util.List;

public class AnimalesRepository {
    private List<Animales> animales;
    private int contadorId;

    public AnimalesRepository() {
        this.animales = new ArrayList<>();
        this.contadorId = 1;
    }

    public Animales crear(Animales animal) {
        animales.add(animal);
        return animal;
    }

    public Animales buscarPorId(int idAnimal) {
        for (Animales animal : animales) {
            if (animal.getIdAnimal() == idAnimal) {
                return animal;
            }
        }
        return null;
    }

    public List<Animales> obtenerTodos() {
        return new ArrayList<>(animales);
    }

    public Animales actualizar(Animales animalActualizado) {
        for (int i = 0; i < animales.size(); i++) {
            if (animales.get(i).getIdAnimal() == animalActualizado.getIdAnimal()) {
                animales.set(i, animalActualizado);
                return animalActualizado;
            }
        }
        return null;
    }

    public boolean eliminar(int idAnimal) {
        for (int i = 0; i < animales.size(); i++) {
            if (animales.get(i).getIdAnimal() == idAnimal) {
                animales.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Animales> buscarPorRaza(String raza) {
        List<Animales> resultado = new ArrayList<>();
        for (Animales animal : animales) {
            if (animal.getRaza().equals(raza)) {
                resultado.add(animal);
            }
        }
        return resultado;
    }
    public List<Animales> buscarPorSexo(boolean sexo) {
        List<Animales> resultado = new ArrayList<>();
        for (Animales animal : animales) {
            if (animal.isSexo() == sexo) {
                resultado.add(animal);
            }
        }
        return resultado;
    }

    public int obtenerTotal() {
        return animales.size();
    }
}