package Routes;

import Controller.RolController;
import io.javalin.Javalin;

public class RolRoutes {

    private final RolController rolController;

    public RolRoutes(RolController rolController) {
        this.rolController = rolController;
    }

    public void register(Javalin app) {
        app.get("/roles", rolController::obtenerTodosRoles);
        app.get("/roles/{id}", rolController::buscarRolPorId);
        app.get("/roles/nombre", rolController::buscarRolPorNombre);
        app.post("/roles", rolController::crearRol);
        app.put("/roles/{id}", rolController::actualizarRol);
        app.delete("/roles/{id}", rolController::eliminarRol);
    }
}