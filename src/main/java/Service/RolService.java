package Service;

import Model.Rol;
import Repository.RolRepository;
import java.util.List;
import java.util.Optional;

public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Rol crearRol(Rol rol) {
        if (rol.getNombre() == null || rol.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol es obligatorio.");
        }

        if (rolRepository.buscarPorNombre(rol.getNombre()) != null) {
            throw new IllegalArgumentException("Ya existe un rol con el nombre: " + rol.getNombre());
        }

        return rolRepository.crear(rol);
    }

    public List<Rol> obtenerTodos() {
        return rolRepository.obtenerTodos();
    }

    public Optional<Rol> buscarPorId(int id) {
        Rol rol = rolRepository.buscarPorId(id);
        return Optional.ofNullable(rol);
    }

    public Rol actualizarRol(Rol rolActualizado) {
        if (rolRepository.buscarPorId(rolActualizado.getIdRol()) == null) {
            return null;
        }

        if (rolActualizado.getNombre() == null || rolActualizado.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol es obligatorio.");
        }

        return rolRepository.actualizar(rolActualizado);
    }

    public boolean eliminarRol(int id) {
        return rolRepository.eliminar(id);
    }

    public Optional<Rol> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El parámetro de búsqueda 'nombre' no puede ser vacío.");
        }
        Rol rol = rolRepository.buscarPorNombre(nombre);
        return Optional.ofNullable(rol);
    }
}