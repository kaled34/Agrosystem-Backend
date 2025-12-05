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
    private final TokenManager tokenManager;

    public UsuarioController(UsuarioRepository usuarioRepository, TokenManager tokenManager) {
        this.usuarioRepository = usuarioRepository;
        this.tokenManager = tokenManager;
    }

    /**
     * Maneja la autenticación de usuarios con credenciales.
     * Valida las credenciales y genera un token JWT si son correctas.
     * 
     * @param ctx Contexto de Javalin (body JSON con "nombre" y "contrasena"
     *            requeridos)
     * 
     *            <p>
     *            Ejemplo de body JSON:
     *            </p>
     * 
     *            <pre>
     * {
     *   "nombre": "admin",
     *   "contrasena": "password123"
     * }
     *            </pre>
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Login exitoso, retorna token JWT y datos del
     *            usuario</li>
     *            <li>400 Bad Request - Credenciales faltantes</li>
     *            <li>401 Unauthorized - Credenciales incorrectas</li>
     *            <li>500 Internal Server Error - Error del servidor</li>
     *            </ul>
     */
    public void login(Context ctx) {
        try {
            Map<String, String> body = ctx.bodyAsClass(Map.class);
            String nombre = body.get("nombre");
            String contrasena = body.get("contrasena");

            if (nombre == null || nombre.isEmpty() || contrasena == null || contrasena.isEmpty()) {
                ctx.status(400).json(Map.of(
                        "success", false,
                        "message", "Nombre y contraseña son requeridos"));
                return;
            }

            Usuario usuario = usuarioRepository.validarCredenciales(nombre, contrasena);

            if (usuario != null) {
                String token = tokenManager.issueToken(String.valueOf(usuario.getIdUsuario()));

                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login exitoso");
                response.put("token", token);

                Map<String, Object> usuarioData = new HashMap<>();
                usuarioData.put("idUsuario", usuario.getIdUsuario());
                usuarioData.put("nombre", usuario.getNombreUsuario());
                usuarioData.put("rol", usuario.getRol().getNombre());

                response.put("usuario", usuarioData);
                ctx.status(200).json(response);
            } else {
                ctx.status(401).json(Map.of(
                        "success", false,
                        "message", "Credenciales incorrectas"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "message", "Error en el servidor: " + e.getMessage()));
        }
    }

    /**
     * Maneja la creación de un nuevo usuario en el sistema.
     * 
     * @param ctx Contexto de Javalin (body JSON con datos del usuario requerido)
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>201 Created - Usuario creado exitosamente</li>
     *            <li>500 Internal Server Error - Error al crear usuario</li>
     *            </ul>
     */
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
            ctx.status(500).json(Map.of(
                    "success", false,
                    "message", "Error al crear usuario: " + e.getMessage()));
        }
    }

    /**
     * Obtiene la lista de todos los usuarios registrados.
     * 
     * @param ctx Contexto de Javalin
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Lista de usuarios en JSON</li>
     *            <li>500 Internal Server Error - Error al obtener usuarios</li>
     *            </ul>
     */
    public void obtenerTodos(Context ctx) {
        try {
            ctx.status(200).json(usuarioRepository.obtenerTodos());
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "message", "Error al obtener usuarios: " + e.getMessage()));
        }
    }

    /**
     * Busca un usuario por su ID.
     * 
     * @param ctx Contexto de Javalin (path parameter "id" requerido)
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Usuario encontrado</li>
     *            <li>404 Not Found - Usuario no encontrado</li>
     *            <li>500 Internal Server Error - Error en la búsqueda</li>
     *            </ul>
     */
    public void buscarPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Usuario usuario = usuarioRepository.obtenerPorId(id);

            if (usuario != null) {
                ctx.status(200).json(usuario);
            } else {
                ctx.status(404).json(Map.of(
                        "success", false,
                        "message", "Usuario no encontrado"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "message", "Error al buscar usuario: " + e.getMessage()));
        }
    }

    /**
     * Actualiza la información de un usuario existente.
     * 
     * @param ctx Contexto de Javalin (body JSON con datos actualizados del usuario)
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Usuario actualizado exitosamente</li>
     *            <li>404 Not Found - Usuario no encontrado</li>
     *            <li>500 Internal Server Error - Error al actualizar</li>
     *            </ul>
     */
    public void actualizar(Context ctx) {
        try {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            Usuario usuarioActualizado = usuarioRepository.actualizar(usuario);

            if (usuarioActualizado != null) {
                ctx.status(200).json(Map.of(
                        "success", true,
                        "message", "Usuario actualizado exitosamente",
                        "usuario", usuarioActualizado));
            } else {
                ctx.status(404).json(Map.of(
                        "success", false,
                        "message", "Usuario no encontrado"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "message", "Error al actualizar usuario: " + e.getMessage()));
        }
    }

    /**
     * Elimina un usuario del sistema por su ID.
     * 
     * @param ctx Contexto de Javalin (path parameter "id" requerido)
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Usuario eliminado exitosamente</li>
     *            <li>404 Not Found - Usuario no encontrado</li>
     *            <li>500 Internal Server Error - Error al eliminar</li>
     *            </ul>
     */
    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean eliminado = usuarioRepository.eliminar(id);

            if (eliminado) {
                ctx.status(200).json(Map.of(
                        "success", true,
                        "message", "Usuario eliminado exitosamente"));
            } else {
                ctx.status(404).json(Map.of(
                        "success", false,
                        "message", "Usuario no encontrado"));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "message", "Error al eliminar usuario: " + e.getMessage()));
        }
    }

    /**
     * Busca un usuario por su nombre.
     * 
     * @param ctx Contexto de Javalin (query parameter "nombre" requerido)
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Usuario encontrado</li>
     *            <li>400 Bad Request - Parámetro nombre faltante</li>
     *            <li>404 Not Found - Usuario no encontrado</li>
     *            <li>500 Internal Server Error - Error en la búsqueda</li>
     *            </ul>
     */
    public void buscarPorNombre(Context ctx) {
        try {
            String nombre = ctx.queryParam("nombre");

            if (nombre == null || nombre.trim().isEmpty()) {
                ctx.status(400).json(Map.of(
                        "success", false,
                        "message", "El parámetro 'nombre' es requerido"));
                return;
            }

            Usuario usuario = usuarioRepository.buscarPorNombre(nombre);

            if (usuario != null) {
                ctx.status(200).json(usuario);
            } else {
                ctx.status(404).json(Map.of(
                        "success", false,
                        "message", "No se encontró usuario con el nombre: " + nombre));
            }
        } catch (Exception e) {
            ctx.status(500).json(Map.of(
                    "success", false,
                    "message", "Error al buscar usuario por nombre: " + e.getMessage()));
        }
    }

    /**
     * Busca todos los usuarios que tienen un rol específico.
     * 
     * @param ctx Contexto de Javalin (query parameter "idRol" requerido)
     * 
     *            <p>
     *            Respuestas:
     *            </p>
     *            <ul>
     *            <li>200 OK - Lista de usuarios con el rol especificado</li>
     *            <li>204 No Content - No se encontraron usuarios con ese rol</li>
     *            <li>500 Internal Server Error - Error en la búsqueda</li>
     *            </ul>
     */
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
            ctx.status(500).json(Map.of(
                    "success", false,
                    "message", "Error al buscar usuarios por rol: " + e.getMessage()));
        }
    }
}