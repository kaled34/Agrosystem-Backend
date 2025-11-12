package Repository;

import Config.ConfigDB;
import Model.Animales;
import Model.Raza;

import Config.ConfigDB;
import Model.Animales;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalesRepository {

    public Animales crear(Animales animal) {
        String sql = "INSERT INTO animales (numArete, nombreAnimal, fechaNacimiento, fechaDestete, " +
                "fecha1erParto, fecha1erMonta, numCrias, descripcionAnimal, estadoActual, raza) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, animal.getNumArete());
            stmt.setString(2, animal.getNombreAnimal());
            stmt.setDate(3, animal.getFechaNacimiento() != null ? Date.valueOf(animal.getFechaNacimiento()) : null);
            stmt.setDate(4, animal.getFechaDestete() != null ? Date.valueOf(animal.getFechaDestete()) : null);
            stmt.setDate(5, animal.getFecha1erParto() != null ? Date.valueOf(animal.getFecha1erParto()) : null);
            stmt.setDate(6, animal.getFecha1erMonta() != null ? Date.valueOf(animal.getFecha1erMonta()) : null);
            stmt.setInt(7, animal.getNumCrias());
            stmt.setString(8, animal.getDescripcionAnimal());
            stmt.setString(9, animal.getEstadoActual());
            stmt.setString(10, animal.getRaza());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    animal.idAnimal = rs.getInt(1);
                }
            }

            return animal;

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear animal: " + e.getMessage(), e);
        }
    }

    public Animales buscarPorId(int idAnimal) {
        String sql = "SELECT * FROM animales WHERE idAnimal = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAnimal);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAnimal(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar animal: " + e.getMessage(), e);
        }

        return null;
    }

    public List<Animales> obtenerTodos() {
        List<Animales> animales = new ArrayList<>();
        String sql = "SELECT * FROM animales";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                animales.add(mapearAnimal(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener animales: " + e.getMessage(), e);
        }

        return animales;
    }

    public Animales actualizar(Animales animal) {
        String sql = "UPDATE animales SET numArete = ?, nombreAnimal = ?, " +
                "fechaNacimiento = ?, fechaDestete = ?, fecha1erParto = ?, " +
                "fecha1erMonta = ?, raza = ?, numCrias = ?, descripcionAnimal = ?, " +
                "estadoActual = ? WHERE idAnimal = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, animal.getNumArete());
            stmt.setString(2, animal.getNombreAnimal());
            stmt.setDate(3, animal.getFechaNacimiento() != null ? Date.valueOf(animal.getFechaNacimiento()) : null);
            stmt.setDate(4, animal.getFechaDestete() != null ? Date.valueOf(animal.getFechaDestete()) : null);
            stmt.setDate(5, animal.getFecha1erParto() != null ? Date.valueOf(animal.getFecha1erParto()) : null);
            stmt.setDate(6, animal.getFecha1erMonta() != null ? Date.valueOf(animal.getFecha1erMonta()) : null);
            stmt.setString(7, animal.getRaza());
            stmt.setInt(8, animal.getNumCrias());
            stmt.setString(9, animal.getDescripcionAnimal());
            stmt.setString(10, animal.getEstadoActual());
            stmt.setInt(11, animal.getIdAnimal());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return animal;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar animal: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean eliminar(int idAnimal) {
        String sql = "DELETE FROM animales WHERE idAnimal = ?" + "SELECT * FROM reportemedico WHERE idAnimal = ?" +
                "SELECT * FROM tarjetasalud WHERE idAnimal = ?" +
                "SELECT * FROM tratamiento WHERE idAnimal = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAnimal);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar animal: " + e.getMessage(), e);
        }
    }

    public List<Animales> buscarPorRaza(String raza) {
        List<Animales> animales = new ArrayList<>();
        String sql = "SELECT * FROM animales WHERE raza = ?";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, raza);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    animales.add(mapearAnimal(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar por raza: " + e.getMessage(), e);
        }

        return animales;
    }

    public int obtenerTotal() {
        String sql = "SELECT COUNT(*) FROM animales";

        try (Connection conn = ConfigDB.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener total: " + e.getMessage(), e);
        }

        return 0;
    }

    private Animales mapearAnimal(ResultSet rs) throws SQLException {
        Animales animal = new Animales();
        animal.idAnimal = rs.getInt("idAnimal");
        animal.numArete = rs.getInt("numArete");
        animal.nombreAnimal = rs.getString("nombreAnimal");

        Date fechaNac = rs.getDate("fechaNacimiento");
        animal.fechaNacimiento = fechaNac != null ? fechaNac.toLocalDate() : null;

        Date fechaDest = rs.getDate("fechaDestete");
        animal.fechaDestete = fechaDest != null ? fechaDest.toLocalDate() : null;

        Date fecha1erP = rs.getDate("fecha1erParto");
        animal.fecha1erParto = fecha1erP != null ? fecha1erP.toLocalDate() : null;

        Date fecha1erM = rs.getDate("fecha1erMonta");
        animal.fecha1erMonta = fecha1erM != null ? fecha1erM.toLocalDate() : null;

        animal.raza = rs.getString("raza");
        animal.numCrias = rs.getInt("numCrias");
        animal.descripcionAnimal = rs.getString("descripcionAnimal");
        animal.estadoActual = rs.getString("estadoActual");

        return animal;
    }
}