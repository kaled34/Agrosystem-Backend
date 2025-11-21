package Routes;

import Controller.PartoController;
import io.javalin.Javalin;

public class PartoRoutes {

    private final PartoController partoController;

    public PartoRoutes(PartoController partoController) {
        this.partoController = partoController;
    }

    public void register(Javalin app) {
        app.get("/partos", partoController::obtenerTodosPartos);
        app.get("/partos/{id}", partoController::buscarPartoPorId);
        app.post("/partos", partoController::crearParto);
        app.put("/partos/{id}", partoController::actualizarParto);
        app.delete("/partos/{id}", partoController::eliminarParto);
    }
}