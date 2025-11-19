package Routes;

import Controller.EstadisticasController;
import io.javalin.Javalin;

public class EstadisticasRoutes {

    private final EstadisticasController estadisticasController;

    public EstadisticasRoutes(EstadisticasController estadisticasController) {
        this.estadisticasController = estadisticasController;
    }

    public void register(Javalin app) {
        app.get("/estadisticas/animales", estadisticasController::obtenerEstadisticasAnimales);
    }
}