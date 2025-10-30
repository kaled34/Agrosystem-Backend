package Controller;

import io.javalin.http.Context;
import Model.Medicamento;
import Service.MedicamentoService;
import java.util.List;
import java.util.Optional;


public class MedicamentoController {

    private final MedicamentoService medicamentoService;


    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }


    public void obtenerTodosMedicamentos(Context ctx) {
        List<Medicamento> medicamentos = medicamentoService.obtenerTodos();

        if (medicamentos.isEmpty()) {

            ctx.status(204).result("No hay medicamentos registrados.");
        } else {

            ctx.status(200).json(medicamentos);
        }
    }


    public void buscarMedicamentoPorId(Context ctx) {

        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<Medicamento> medicamento = medicamentoService.buscarPorId(id);

        if (medicamento.isPresent()) {
            ctx.status(200).json(medicamento.get());
        } else {

            ctx.status(404).result("Medicamento con ID " + id + " no encontrado.");
        }
    }


    public void crearMedicamento(Context ctx) {
        try {

            Medicamento nuevoMedicamento = ctx.bodyAsClass(Medicamento.class);


            Medicamento medicamentoCreado = medicamentoService.crearMedicamento(nuevoMedicamento);


            ctx.status(201).json(medicamentoCreado);

        } catch (IllegalArgumentException e) {

            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {

            ctx.status(500).result("Error interno del servidor al procesar la solicitud: " + e.getMessage());
        }
    }


    public void actualizarMedicamento(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Medicamento medicamentoActualizado = ctx.bodyAsClass(Medicamento.class);


            if (medicamentoActualizado.getIdMedicamento() != id) {
                ctx.status(400).result("El ID del cuerpo no coincide con el ID de la ruta.");
                return;
            }

            Medicamento resultado = medicamentoService.actualizarMedicamento(medicamentoActualizado);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {

                ctx.status(404).result("Medicamento con ID " + id + " no encontrado para actualizar.");
            }

        } catch (IllegalArgumentException e) {
            // 400 Bad Request por error de validación
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }


    public void eliminarMedicamento(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (medicamentoService.eliminarMedicamento(id)) {
            // 204 No Content (Éxito sin devolver cuerpo)
            ctx.status(204).result("Medicamento eliminado exitosamente.");
        } else {
            // 404 Not Found si no se pudo eliminar (porque no existía)
            ctx.status(404).result("Medicamento con ID " + id + " no encontrado o no se pudo eliminar.");
        }
    }
}
