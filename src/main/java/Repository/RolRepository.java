package Repository;
import Model.Rol;

import java.util.ArrayList;
import java.util.List;

public class RolRepository {
    private List<Rol> roles;

    public RolRepository() {
        this.roles = new ArrayList<>();
    }

    public Rol crear(Rol rol) {
        roles.add(rol);
        return rol;
    }

    public Rol buscarPorId(int idRol) {
        for (Rol rol : roles) {
            if (rol.getIdRol() == idRol) {
                return rol;
            }
        }
        return null;
    }

    public List<Rol> obtenerTodos() {
        return new ArrayList<>(roles);
    }

    public Rol actualizar(Rol rolActualizado) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getIdRol() == rolActualizado.getIdRol()) {
                roles.set(i, rolActualizado);
                return rolActualizado;
            }
        }
        return null;
    }

    public boolean eliminar(int idRol) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getIdRol() == idRol) {
                roles.remove(i);
                return true;
            }
        }
        return false;
    }

    public Rol buscarPorNombre(String nombre) {
        for (Rol rol : roles) {
            if (rol.getNombre().equalsIgnoreCase(nombre)) {
                return rol;
            }
        }
        return null;
    }

    public int obtenerTotal() {
        return roles.size();
    }
}