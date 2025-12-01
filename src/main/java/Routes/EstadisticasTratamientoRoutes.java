package Routes;

import Controller.EstadisticasTratamientoController;
import io.javalin.Javalin;

public class EstadisticasTratamientoRoutes {

    private final EstadisticasTratamientoController estadisticasTratamientoController;

    public EstadisticasTratamientoRoutes(EstadisticasTratamientoController estadisticasTratamientoController) {
        this.estadisticasTratamientoController = estadisticasTratamientoController;
    }

    public void register(Javalin app) {
        app.get("/api/estadisticas/tratamiento-vs-sanos", estadisticasTratamientoController::obtenerEstadisticaTratamientoVsSanos);
    }
}