package Controller;

import Service.EstadisticaMedicamentoService;
import  io.javalin.http.Context;

public class EstadisticaMedicamentoController {
    private  final EstadisticaMedicamentoService estadisticaMedicamentoService;

    public EstadisticaMedicamentoController (EstadisticaMedicamentoService estadisticaMedicamentoService){
        this.estadisticaMedicamentoService = estadisticaMedicamentoService;
    }
    public void obtenerMedicamentoMasUsado(Context ctx) {
        try {
            ctx.status(200).json(estadisticaMedicamentoService.obtenerMedicamentoMasUsado());
        } catch (Exception e) {
            ctx.status(500).result("error al obtener la estadisticaaaa: " + e.getMessage());
        }
    }
}
