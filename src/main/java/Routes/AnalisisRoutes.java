package Routes;

import Controller.AnalisisController;
import io.javalin.Javalin;

public class AnalisisRoutes {

    private final AnalisisController analisisController;

    public AnalisisRoutes(AnalisisController analisisController) {
        this.analisisController = analisisController;
    }

    public void register(Javalin app) {
        app.get("/analisis", analisisController::obtenerTodosAnalisis);
        app.get("/analisis/{id}", analisisController::buscarAnalisisPorId);
        app.post("/analisis", analisisController::crearAnalisis);
        app.put("/analisis/{id}", analisisController::actualizarAnalisis);
        app.delete("/analisis/{id}", analisisController::eliminarAnalisis);
    }
}