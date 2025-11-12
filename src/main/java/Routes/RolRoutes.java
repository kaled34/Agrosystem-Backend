package Routes;

import Controller.RolController;
import io.javalin.Javalin;

public class RolRoutes {

    private final RolController rolController;

    public RolRoutes(RolController rolController) {
        this.rolController = rolController;
    }

    public void register(Javalin app) {
        app.get("http://localhost:7000/rol", rolController::obtenerTodosRoles);
        app.get("http://localhost:7000/rol/{id}", rolController::buscarRolPorId);
        app.get("http://localhost:7000/rol/nombre", rolController::buscarRolPorNombre);
        app.post("http://localhost:7000/rol", rolController::crearRol);
        app.put("http://localhost:7000/rol/{id}", rolController::actualizarRol);
        app.delete("http://localhost:7000/rol/{id}", rolController::eliminarRol);
    }
}