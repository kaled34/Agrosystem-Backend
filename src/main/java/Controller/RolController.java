package Controller;

import io.javalin.http.Context;
import Model.Rol;
import Service.RolService;
import java.util.List;
import java.util.Optional;

public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    public void obtenerTodosRoles(Context ctx) {
        List<Rol> roles = rolService.obtenerTodos();

        if (roles.isEmpty()) {
            ctx.status(204).result("No hay roles registrados.");
        } else {
            ctx.status(200).json(roles);
        }
    }

    public void buscarRolPorId(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        Optional<Rol> rol = rolService.buscarPorId(id);

        if (rol.isPresent()) {
            ctx.status(200).json(rol.get());
        } else {
            ctx.status(404).result("Rol con ID " + id + " no encontrado.");
        }
    }

    public void crearRol(Context ctx) {
        try {
            Rol nuevoRol = ctx.bodyAsClass(Rol.class);

            Rol rolCreado = rolService.crearRol(nuevoRol);

            ctx.status(201).json(rolCreado);

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Error interno del servidor al procesar la solicitud: " + e.getMessage());
        }
    }

    public void actualizarRol(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        try {
            Rol rolActualizado = ctx.bodyAsClass(Rol.class);

            if (rolActualizado.getIdRol() != id) {
                ctx.status(400).result("El ID del cuerpo no coincide con el ID de la ruta.");
                return;
            }

            Rol resultado = rolService.actualizarRol(rolActualizado);

            if (resultado != null) {
                ctx.status(200).json(resultado);
            } else {
                ctx.status(404).result("Rol con ID " + id + " no encontrado para actualizar.");
            }

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(400).result("Formato de datos inválido: " + e.getMessage());
        }
    }

    public void eliminarRol(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).get();

        if (rolService.eliminarRol(id)) {
            ctx.status(204).result("Rol eliminado exitosamente.");
        } else {
            ctx.status(404).result("Rol con ID " + id + " no encontrado o no se pudo eliminar.");
        }
    }

    public void buscarRolPorNombre(Context ctx) {
        String nombre = ctx.queryParam("nombre");

        try {
            Optional<Rol> rol = rolService.buscarPorNombre(nombre);

            if (rol.isPresent()) {
                ctx.status(200).json(rol.get());
            } else {
                ctx.status(404).result("No se encontró rol con el nombre: " + nombre);
            }
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        }
    }
}