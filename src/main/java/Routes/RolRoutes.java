package Routes;

import Controller.RolController;
import io.javalin.Javalin;

public class RolRoutes {

    private final RolController rolController;

    public RolRoutes(RolController rolController) {
        this.rolController = rolController;
    }

    public void register(Javalin app) {
        app.get("/rol", rolController::obtenerTodosRoles);
        app.get("/rol/buscar-nombre", rolController::buscarRolPorNombre);
        app.get("/rol/{id}", rolController::buscarRolPorId);
        app.post("/rol", rolController::crearRol);
        app.put("/rol/{id}", rolController::actualizarRol);
        app.delete("/rol/{id}", rolController::eliminarRol);
    }
}