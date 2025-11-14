package Routes;

import Controller.AnimalesController;
import io.javalin.Javalin;

public class AnimalesRoutes {

    private final AnimalesController animalesController;

    public AnimalesRoutes(AnimalesController animalesController) {
        this.animalesController = animalesController;
    }

    public void register(Javalin app) {
        app.get("/animal", animalesController::obtenerTodosAnimales);
        app.get("/animal/{id}", animalesController::buscarAnimalPorId);
        app.post("/animal", animalesController::crearAnimal);
        app.put("/animal/{id}", animalesController::actualizarAnimal);
        app.delete("/animal/{id}", animalesController::eliminarAnimal);
    }
}