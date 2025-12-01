package Controller;

import Service.EstadisticasTratamientoService;
import io.javalin.http.Context;

public class EstadisticasTratamientoController {

    private final EstadisticasTratamientoService estadisticasTratamientoService;

    public EstadisticasTratamientoController(EstadisticasTratamientoService estadisticasTratamientoService) {
        this.estadisticasTratamientoService = estadisticasTratamientoService;
    }

    public void obtenerEstadisticaTratamientoVsSanos(Context ctx) {
        try {
            ctx.json(estadisticasTratamientoService.obtenerEstadisticaTratamientoVsSanos());
        } catch (Exception e) {
            ctx.status(500).json(new ErrorResponse("Error al obtener estadísticas: " + e.getMessage()));
        }
    }

    private static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}