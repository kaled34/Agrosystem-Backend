package Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Config.ConfigDB;
import Model.Animales;

public class AnimalesRepository {

    private Connection getConnection() throws SQLException {
        return ConfigDB.getDataSource().getConnection();
    }

    public Animales crear(Animales animal) {
        String sql = "INSERT INTO animal (nombre_animal, num_arete, rebaño, fecha_nacimiento, peso_inicial, caracteristica, edad, procedencia, sexo, id_padre, id_madre, id_propietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, animal.getNombreAnimal());
            stmt.setInt(2, animal.getNumArete());
            stmt.setString(3, animal.getRebaño());
            stmt.setDate(4, Date.valueOf(animal.getFechaNacimiento()));
            stmt.setDouble(5, animal.getPesoInicial());
            stmt.setString(6, animal.getCaracteristica());
            stmt.setInt(7, animal.getEdad());
            stmt.setString(8, animal.getProcedencia());
            stmt.setString(9, animal.isSexo() ? "M" : "F");

            if (animal.getIdPadre() > 0) {
                stmt.setInt(10, animal.getIdPadre());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            if (animal.getIdMadre() > 0) {
                stmt.setInt(11, animal.getIdMadre());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            stmt.setInt(12, animal.getIdPropiertario());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        animal.idAnimal = generatedKeys.getInt(1);
                    }
                }
                return animal;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error al crear animal: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Animales obtenerPorId(int idAnimal) {
        String sql = "SELECT * FROM animal WHERE id_animal = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAnimal(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener animal por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Animales> obtenerTodos() {
        List<Animales> animales = new ArrayList<>();
        String sql = "SELECT * FROM animal";

        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                animales.add(mapearAnimal(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los animales: " + e.getMessage());
            e.printStackTrace();
        }
        return animales;
    }

    public Animales actualizar(Animales animal) {
        String sql = "UPDATE animal SET nombre_animal = ?, num_arete = ?, rebaño = ?, fecha_nacimiento = ?, peso_inicial = ?, caracteristica = ?, edad = ?, procedencia = ?, sexo = ?, id_padre = ?, id_madre = ?, id_propietario = ? WHERE id_animal = ?";
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = getConnection();
            boolean previousAuto = connection.getAutoCommit();
            connection.setAutoCommit(false);

            stmt = connection.prepareStatement(sql);
            stmt.setString(1, animal.getNombreAnimal());
            stmt.setInt(2, animal.getNumArete());
            stmt.setString(3, animal.getRebaño());

            if (animal.getFechaNacimiento() != null) {
                stmt.setDate(4, Date.valueOf(animal.getFechaNacimiento()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setDouble(5, animal.getPesoInicial());
            stmt.setString(6, animal.getCaracteristica());
            stmt.setInt(7, animal.getEdad());
            stmt.setString(8, animal.getProcedencia());

            if (animal.isSexo()) {
                stmt.setString(9, "M");
            } else {
                stmt.setString(9, "F");
            }

            if (animal.getIdPadre() > 0) {
                stmt.setInt(10, animal.getIdPadre());
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            if (animal.getIdMadre() > 0) {
                stmt.setInt(11, animal.getIdMadre());
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            stmt.setInt(12, animal.getIdPropiertario());
            stmt.setInt(13, animal.getIdAnimal());

            try {
                System.out.println("[DEBUG] actualizar (repository) - idAnimal=" + animal.getIdAnimal()
                        + ", idPadre(param)=" + animal.getIdPadre()
                        + ", idMadre(param)=" + animal.getIdMadre());
            } catch (Exception ignore) {
            }

            int updated = stmt.executeUpdate();
            System.out.println("[DEBUG] actualizar - executeUpdate affectedRows=" + updated);
            if (updated > 0) {
                try {
                    connection.commit();
                } catch (SQLException commitEx) {
                    System.err.println("Error al commitear actualización: " + commitEx.getMessage());
                }
                Animales refreshed = null;
                String selectSql = "SELECT * FROM animal WHERE id_animal = ?";
                try (PreparedStatement ps = connection.prepareStatement(selectSql)) {
                    ps.setInt(1, animal.getIdAnimal());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            refreshed = mapearAnimal(rs);
                        }
                    }
                }
                try {
                    connection.setAutoCommit(previousAuto);
                } catch (Exception ex) {
                }
                return refreshed;
            }
            try {
                connection.setAutoCommit(previousAuto);
            } catch (Exception ex) {
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error al actualizar animal: " + e.getMessage());
            e.printStackTrace();
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback en actualizar: " + ex.getMessage());
            }
            return null;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignore) {}
            try {
                if (connection != null) connection.close();
            } catch (SQLException ignore) {}
        }
    }

    public boolean eliminar(int idAnimal) {
        String sqlDeleteAnimal = "DELETE FROM animal WHERE id_animal = ?";
        String sqlDeletePesos = "DELETE FROM PesoAnimal WHERE id_animal = ?";

        try (Connection connection = getConnection()) {
            try {
                connection.setAutoCommit(false);

                try (PreparedStatement delPesos = connection.prepareStatement(sqlDeletePesos)) {
                    delPesos.setInt(1, idAnimal);
                    delPesos.executeUpdate();
                }

                try (PreparedStatement stmt = connection.prepareStatement(sqlDeleteAnimal)) {
                    stmt.setInt(1, idAnimal);
                    int affected = stmt.executeUpdate();
                    connection.commit();
                    return affected > 0;
                }
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error al hacer rollback: " + ex.getMessage());
                }
                System.err.println("Error al eliminar animal: " + e.getMessage());
                e.printStackTrace();
                return false;
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    System.err.println("Error al restaurar autoCommit: " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener conexión para eliminar animal: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean existePorNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM animal WHERE nombre_animal = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean existePorNumArete(int numArete) {
        String sql = "SELECT COUNT(*) FROM animal WHERE num_arete = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numArete);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar num_arete: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Animales obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM animal WHERE nombre_animal = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAnimal(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Animales obtenerPorNumArete(int numArete) {
        String sql = "SELECT * FROM animal WHERE num_arete = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numArete);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAnimal(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener por num_arete: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private Animales mapearAnimal(ResultSet rs) throws SQLException {
        return new Animales(
                rs.getInt("id_animal"),
                rs.getString("nombre_animal"),
                rs.getInt("num_arete"),
                rs.getString("rebaño"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getDouble("peso_inicial"),
                rs.getString("caracteristica"),
                rs.getInt("edad"),
                rs.getString("procedencia"),
                rs.getString("sexo").equals("M"),
                rs.getInt("id_padre"),
                rs.getInt("id_madre"),
                rs.getInt("id_propietario")
        );
    }
}