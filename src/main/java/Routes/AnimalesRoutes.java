package Routes;

import Controller.AnimalesController;
import io.javalin.Javalin;

public class AnimalesRoutes {

    private final AnimalesController animalesController;

    public AnimalesRoutes(AnimalesController animalesController) {
        this.animalesController = animalesController;
    }

    public void register(Javalin app) {
        app.get("/animales", animalesController::obtenerTodosAnimales);
        app.get("/animales/{id}", animalesController::buscarAnimalPorId);
        app.post("/animales", animalesController::crearAnimal);
        app.put("/animales/{id}", animalesController::actualizarAnimal);
        app.delete("/animales/{id}", animalesController::eliminarAnimal);
    }
}