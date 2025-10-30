package Routes;

import Controller.MedicamentoController;
import io.javalin.Javalin;

public class MedicamentoRoutes {

    private final MedicamentoController medicamentoController;

    public MedicamentoRoutes(MedicamentoController medicamentoController) {
        this.medicamentoController = medicamentoController;
    }

    public void register(Javalin app) {
        app.get("/medicamentos", medicamentoController::obtenerTodosMedicamentos);
        app.get("/medicamentos/{id}", medicamentoController::buscarMedicamentoPorId);
        app.post("/medicamentos", medicamentoController::crearMedicamento);
        app.put("/medicamentos/{id}", medicamentoController::actualizarMedicamento);
        app.delete("/medicamentos/{id}", medicamentoController::eliminarMedicamento);
    }
}