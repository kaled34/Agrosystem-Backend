package Routes;

import Controller.EstadisticaMedicamentoController;
import io.javalin.Javalin;

public class EstadisticaMedicamentoRoutes {

    private final EstadisticaMedicamentoController estadisticaMedicamentoController;

    public EstadisticaMedicamentoRoutes(EstadisticaMedicamentoController estadisticaMedicamentoController) {
        this.estadisticaMedicamentoController = estadisticaMedicamentoController;
    }

    public void register(Javalin app) {
        app.get("/estadisticas/medicamento-mas-usado", estadisticaMedicamentoController::obtenerMedicamentoMasUsado);
    }
}