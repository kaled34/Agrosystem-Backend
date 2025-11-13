package Repository;

import Model.Animales;
import Config.ConfigDB;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnimalesRepository {
    private Connection connection;

    // ✅ CORREGIDO: Constructor sin parámetros
    public AnimalesRepository() {
        try {
            this.connection = ConfigDB.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Animales crear(Animales animal) {
        String sql = "INSERT INTO Animal (nombre_animal, num_arete, rebaño, fecha_nac, peso_inicial, caracteristica, edad, procedencia, sexo, id_padre, id_madre, id_propietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            e.printStackTrace();
            return null;
        }
    }

    public Animales obtenerPorId(int idAnimal) {
        String sql = "SELECT * FROM Animal WHERE id_animal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearAnimal(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Animales obtenerPorNumArete(int numArete) {
        String sql = "SELECT * FROM Animal WHERE num_arete = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numArete);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapearAnimal(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Animales> obtenerTodos() {
        List<Animales> animales = new ArrayList<>();
        String sql = "SELECT * FROM Animal";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                animales.add(mapearAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }

    public List<Animales> obtenerPorRebaño(String rebaño) {
        List<Animales> animales = new ArrayList<>();
        String sql = "SELECT * FROM Animal WHERE rebaño = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, rebaño);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animales.add(mapearAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }

    public List<Animales> obtenerPorSexo(boolean sexo) {
        List<Animales> animales = new ArrayList<>();
        String sql = "SELECT * FROM Animal WHERE sexo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sexo ? "M" : "F");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animales.add(mapearAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }

    public List<Animales> obtenerPorPropietario(int idPropietario) {
        List<Animales> animales = new ArrayList<>();
        String sql = "SELECT * FROM Animal WHERE id_propietario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPropietario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animales.add(mapearAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }

    public List<Animales> obtenerCrias(int idPadreOMadre, boolean esPadre) {
        List<Animales> animales = new ArrayList<>();
        String sql = esPadre ?
                "SELECT * FROM Animal WHERE id_padre = ?" :
                "SELECT * FROM Animal WHERE id_madre = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPadreOMadre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                animales.add(mapearAnimal(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animales;
    }

    public Animales actualizar(Animales animal) {
        String sql = "UPDATE Animal SET nombre_animal = ?, num_arete = ?, rebaño = ?, caracteristica = ? WHERE id_animal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, animal.getNombreAnimal());
            stmt.setInt(2, animal.getNumArete());
            stmt.setString(3, animal.getRebaño());
            stmt.setString(4, animal.getCaracteristica());
            stmt.setInt(5, animal.getIdAnimal());

            if (stmt.executeUpdate() > 0) {
                return animal;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean eliminar(int idAnimal) {
        String sql = "DELETE FROM Animal WHERE id_animal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para mapear ResultSet a objeto Animales
    private Animales mapearAnimal(ResultSet rs) throws SQLException {
        return new Animales(
                rs.getInt("id_animal"),
                rs.getString("nombre_animal"),
                rs.getInt("num_arete"),
                rs.getString("rebaño"),
                rs.getDate("fecha_nac").toLocalDate(),
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