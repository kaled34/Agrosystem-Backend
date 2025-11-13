package Controller;

import Model.Usuario;
import Model.Rol;
import Repository.UsuarioRepository;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void login(Context ctx) {
        try {

            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String nombre = body.get("nombre");
            String contrasena = body.get("contrasena");


            if (nombre == null || nombre.isEmpty() || contrasena == null || contrasena.isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Nombre y contraseña son requeridos");
                ctx.status(400).json(response);
                return;
            }


            Usuario usuario = usuarioRepository.validarCredenciales(nombre, contrasena);

            if (usuario != null) {

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login exitoso");

                Map<String, Object> usuarioData = new HashMap<>();
                usuarioData.put("idUsuario", usuario.getIdUsuario());
                usuarioData.put("nombre", usuario.getNombreUsuario());
                usuarioData.put("rol", usuario.getRol().getNombre());

                response.put("usuario", usuarioData);
                ctx.status(200).json(response);
            } else {
                // Credenciales incorrectas
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Credenciales incorrectas");
                ctx.status(401).json(response);
            }

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error en el servidor: " + e.getMessage());
            ctx.status(500).json(response);
        }
    }

    public void crearUsuario(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            Usuario nuevoUsuario = usuarioRepository.crear(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Usuario creado exitosamente");
            response.put("usuario", nuevoUsuario);
            ctx.status(201).json(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al crear usuario: " + e.getMessage());
            ctx.status(500).json(response);
        }
    }

    public void obtenerTodos(Context ctx) {
        try {
            ctx.status(200).json(usuarioRepository.obtenerTodos());
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al obtener usuarios: " + e.getMessage());
            ctx.status(500).json(response);
        }
    }

    public void buscarPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuario = usuarioRepository.obtenerPorId(id);

            if (usuario != null) {
                ctx.status(200).json(usuario);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Usuario no encontrado");
                ctx.status(404).json(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al buscar usuario: " + e.getMessage());
            ctx.status(500).json(response);
        }
    }

    public void actualizar(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            Usuario usuarioActualizado = usuarioRepository.actualizar(usuario);

            if (usuarioActualizado != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Usuario actualizado exitosamente");
                response.put("usuario", usuarioActualizado);
                ctx.status(200).json(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Usuario no encontrado");
                ctx.status(404).json(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al actualizar usuario: " + e.getMessage());
            ctx.status(500).json(response);
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean eliminado = usuarioRepository.eliminar(id);

            if (eliminado) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Usuario eliminado exitosamente");
                ctx.status(200).json(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Usuario no encontrado");
                ctx.status(404).json(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al eliminar usuario: " + e.getMessage());
            ctx.status(500).json(response);
        }
    }

    public void buscarPorNombre(Context ctx) {
        try {
            String nombre = ctx.queryParam("nombre");

            if (nombre == null || nombre.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "El parámetro 'nombre' es requerido");
                ctx.status(400).json(response);
                return;
            }

            Usuario usuario = usuarioRepository.buscarPorNombre(nombre);

            if (usuario != null) {
                ctx.status(200).json(usuario);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "No se encontró usuario con el nombre: " + nombre);
                ctx.status(404).json(response);
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al buscar usuario por nombre: " + e.getMessage());
            ctx.status(500).json(response);
        }
    }

    public void buscarPorRol(Context ctx) {
        try {
            int idRol = ctx.queryParamAsClass("idRol", Integer.class).get();

            Rol rol = new Rol(idRol, "");
            List<Usuario> usuarios = usuarioRepository.buscarPorRol(rol);

            if (!usuarios.isEmpty()) {
                ctx.status(200).json(usuarios);
            } else {
                ctx.status(204).result("No se encontraron usuarios con el rol especificado");
            }
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al buscar usuarios por rol: " + e.getMessage());
            ctx.status(500).json(response);
        }
    }
}