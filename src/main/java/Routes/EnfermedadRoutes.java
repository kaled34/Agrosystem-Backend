package Routes;

import Controller.EnfermedadController;
import io.javalin.Javalin;

public class EnfermedadRoutes {

    private final EnfermedadController enfermedadController;

    public EnfermedadRoutes(EnfermedadController enfermedadController) {
        this.enfermedadController = enfermedadController;
    }

    public void register(Javalin app) {
        app.get("/enfermedades", enfermedadController::obtenerTodasEnfermedades);
        app.get("/enfermedades/{id}", enfermedadController::buscarEnfermedadPorId);
        app.post("/enfermedades", enfermedadController::crearEnfermedad);
        app.put("/enfermedades/{id}", enfermedadController::actualizarEnfermedad);
        app.delete("/enfermedades/{id}", enfermedadController::eliminarEnfermedad);
    }
}