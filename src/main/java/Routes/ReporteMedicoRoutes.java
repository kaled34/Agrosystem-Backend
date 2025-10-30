package Routes;

import Controller.ReporteMedicoController;
import io.javalin.Javalin;

public class ReporteMedicoRoutes {

    private final ReporteMedicoController reporteMedicoController;

    public ReporteMedicoRoutes(ReporteMedicoController reporteMedicoController) {
        this.reporteMedicoController = reporteMedicoController;
    }

    public void register(Javalin app) {
        app.get("/reportes", reporteMedicoController::obtenerTodosReportes);
        app.get("/reportes/{id}", reporteMedicoController::buscarReportePorId);
        app.get("/reportes/animal", reporteMedicoController::buscarReportesPorAnimal);
        app.post("/reportes", reporteMedicoController::crearReporte);
        app.put("/reportes/{id}", reporteMedicoController::actualizarReporte);
        app.delete("/reportes/{id}", reporteMedicoController::eliminarReporte);
    }
}