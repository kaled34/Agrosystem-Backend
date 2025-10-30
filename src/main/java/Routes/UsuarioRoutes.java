package Routes;

import Controller.UsuarioController;
import io.javalin.Javalin;

public class UsuarioRoutes {
    private final UsuarioController usuarioController;

    public UsuarioRoutes(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void register(Javalin app) {
        app.get("/usuarios", usuarioController::obtenerTodos);
        app.get("/usuarios/{id}", usuarioController::buscarPorId);
        app.post("/usuarios", usuarioController::crearUsuario);
        app.put("/usuarios/{id}", usuarioController::actualizar);
        app.delete("/usuarios/{id}", usuarioController::eliminar);
    }
}