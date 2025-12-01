package Service;

import Model.EstadisticasTratamiento;
import Repository.EstadisticasTratamientoRepository;

public class EstadisticasTratamientoService {

    private final EstadisticasTratamientoRepository estadisticasTratamientoRepository;

    public EstadisticasTratamientoService(EstadisticasTratamientoRepository estadisticasTratamientoRepository) {
        this.estadisticasTratamientoRepository = estadisticasTratamientoRepository;
    }

    public EstadisticasTratamiento obtenerEstadisticaTratamientoVsSanos() {
        EstadisticasTratamiento estadistica = estadisticasTratamientoRepository.obtenerEstadisticaTratamientoVsSanos();
        if (estadistica == null) {
            throw new RuntimeException("Error al obtener estadísticas");
        }
        return estadistica;
    }
}
