package Routes;

import Controller.TratamientoController;
import io.javalin.Javalin;

public class TratamientoRoutes {

    private final TratamientoController tratamientoController;

    public TratamientoRoutes(TratamientoController tratamientoController) {
        this.tratamientoController = tratamientoController;
    }

    public void register(Javalin app) {
        app.get("/tratamientos", tratamientoController::obtenerTodosTratamientos);
        app.get("/tratamientos/{id}", tratamientoController::buscarTratamientoPorId);
        app.post("/tratamientos", tratamientoController::crearTratamiento);
        app.put("/tratamientos/{id}", tratamientoController::actualizarTratamiento);
        app.delete("/tratamientos/{id}", tratamientoController::eliminarTratamiento);
    }
}