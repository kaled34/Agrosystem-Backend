package Routes;

import Controller.PesoController;
import io.javalin.Javalin;

public class PesoRoutes {

    private final PesoController pesoController;

    public PesoRoutes(PesoController pesoController) {
        this.pesoController = pesoController;
    }

    public void register(Javalin app) {
        app.get("/pesos", pesoController::obtenerTodosPesos);
        app.get("/pesos/{id}", pesoController::buscarPesoPorId);
        app.post("/pesos", pesoController::crearPeso);
        app.put("/pesos/{id}", pesoController::actualizarPeso);
        app.delete("/pesos/{id}", pesoController::eliminarPeso);
    }
}