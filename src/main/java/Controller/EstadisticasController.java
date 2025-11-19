package Controller;

import io.javalin.http.Context;
import Model.EstadisticaAnimales;
import Service.EstadisticasService;

public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    public EstadisticasController(EstadisticasService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    public void obtenerEstadisticasAnimales(Context ctx) {
        try {
            EstadisticaAnimales estadisticas = estadisticasService.obtenerEstadisticasAnimales();
            ctx.status(200).json(estadisticas);
        } catch (Exception e) {
            ctx.status(500).result("Error al obtener estadísticas: " + e.getMessage());
        }
    }
}