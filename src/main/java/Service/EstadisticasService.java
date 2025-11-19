package Service;

import Model.EstadisticaAnimales;
import Repository.EstadisticasRepository;

public class EstadisticasService {

    private final EstadisticasRepository estadisticasRepository;

    public EstadisticasService(EstadisticasRepository estadisticasRepository) {
        this.estadisticasRepository = estadisticasRepository;
    }

    public EstadisticaAnimales obtenerEstadisticasAnimales() {
        return estadisticasRepository.obtenerEstadisticasAnimales();
    }
}