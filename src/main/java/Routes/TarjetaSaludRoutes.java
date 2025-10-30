package Routes;

import Controller.TarjetaSaludController;
import io.javalin.Javalin;

public class TarjetaSaludRoutes {

    private final TarjetaSaludController tarjetaSaludController;

    public TarjetaSaludRoutes(TarjetaSaludController tarjetaSaludController) {
        this.tarjetaSaludController = tarjetaSaludController;
    }

    public void register(Javalin app) {
        app.get("/tarjetas", tarjetaSaludController::obtenerTodasTarjetas);
        app.get("/tarjetas/{id}", tarjetaSaludController::buscarTarjetaPorId);
        app.get("/tarjetas/animal", tarjetaSaludController::buscarTarjetaPorAnimal);
        app.post("/tarjetas", tarjetaSaludController::crearTarjeta);
        app.put("/tarjetas/{id}", tarjetaSaludController::actualizarTarjeta);
        app.delete("/tarjetas/{id}", tarjetaSaludController::eliminarTarjeta);
    }
}