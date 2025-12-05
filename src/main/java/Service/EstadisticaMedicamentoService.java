package Service;

import Model.EstadisticaMedicamento;
import Repository.EstadisticaMedicamentoRepository;

public class EstadisticaMedicamentoService {

    private final EstadisticaMedicamentoRepository estadisticaMedicamentoRepository;

    public  EstadisticaMedicamentoService(EstadisticaMedicamentoRepository estadisticaMedicamentoRepository){
        this.estadisticaMedicamentoRepository = estadisticaMedicamentoRepository;
    }
    public  EstadisticaMedicamento obtenerMedicamentoMasUsado(){
        EstadisticaMedicamento estadistica = this.estadisticaMedicamentoRepository.obtenerMedicamentoMasUsado();

        if (estadistica == null){
            throw new RuntimeException("Error al obtener la estadistica");
        }
        return  estadistica;
    }
}

