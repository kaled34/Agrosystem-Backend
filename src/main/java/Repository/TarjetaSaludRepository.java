package Repository;

import Model.TarjetaSalud;

import java.util.ArrayList;
import java.util.List;

public class TarjetaSaludRepository {
    private List<TarjetaSalud> tarjetas;

    public TarjetaSaludRepository() {
        this.tarjetas = new ArrayList<>();
    }

    public TarjetaSalud crear(TarjetaSalud tarjeta) {
        tarjetas.add(tarjeta);
        return tarjeta;
    }

    public TarjetaSalud buscarPorId(int idTarjeta) {
        for (TarjetaSalud tarjeta : tarjetas) {
            if (tarjeta.getIdTarjeta() == idTarjeta) {
                return tarjeta;
            }
        }
        return null;
    }

    public List<TarjetaSalud> obtenerTodos() {
        return new ArrayList<>(tarjetas);
    }

    public TarjetaSalud actualizar(TarjetaSalud tarjetaActualizada) {
        for (int i = 0; i < tarjetas.size(); i++) {
            if (tarjetas.get(i).getIdTarjeta() == tarjetaActualizada.getIdTarjeta()) {
                tarjetas.set(i, tarjetaActualizada);
                return tarjetaActualizada;
            }
        }
        return null;
    }

    public boolean eliminar(int idTarjeta) {
        for (int i = 0; i < tarjetas.size(); i++) {
            if (tarjetas.get(i).getIdTarjeta() == idTarjeta) {
                tarjetas.remove(i);
                return true;
            }
        }
        return false;
    }

    public TarjetaSalud buscarPorAnimal(int idAnimal) {
        for (TarjetaSalud tarjeta : tarjetas) {
            if (tarjeta.getIdTarjeta() == idAnimal) {
                return tarjeta;
            }
        }
        return null;
    }

    public int obtenerTotal() {
        return tarjetas.size();
    }
}