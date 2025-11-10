package Routes;

import Controller.MedicamentoController;
import io.javalin.Javalin;

public class MedicamentoRoutes {

    private final MedicamentoController medicamentoController;

    public MedicamentoRoutes(MedicamentoController medicamentoController) {
        this.medicamentoController = medicamentoController;
    }

    public void register(Javalin app) {
        app.get("/medicamento", medicamentoController::obtenerTodosMedicamentos);
        app.get("/medicamento/{id}", medicamentoController::buscarMedicamentoPorId);
        app.post("/medicamento", medicamentoController::crearMedicamento);
        app.put("/medicamento/{id}", medicamentoController::actualizarMedicamento);
        app.delete("/medicamento/{id}", medicamentoController::eliminarMedicamento);
    }
}