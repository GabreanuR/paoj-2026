package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.config.DatabaseConnection;
import com.pao.proiect.fooddelivery.model.Driver;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverRepository implements Repository<Driver, String> {

    @Override
    public void save(Driver driver) throws SQLException {
        String sql = "INSERT INTO soferi (id, nume, telefon, email, numar_inmatriculare, este_disponibil) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, driver.getId());
            stmt.setString(2, driver.getNume());
            stmt.setString(3, driver.getTelefon());
            stmt.setString(4, driver.getEmail());
            stmt.setString(5, driver.getNumarInmatriculare());
            stmt.setBoolean(6, driver.isDisponibil());
            stmt.executeUpdate();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Driver> findById(String id) throws SQLException {
        String sql = "SELECT * FROM soferi WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        } catch (IOException e) { throw new RuntimeException(e); }
        return Optional.empty();
    }

    @Override
    public List<Driver> findAll() throws SQLException {
        List<Driver> soferi = new ArrayList<>();
        String sql = "SELECT * FROM soferi";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) soferi.add(mapRow(rs));
        } catch (IOException e) { throw new RuntimeException(e); }
        return soferi;
    }

    @Override
    public void update(Driver driver) throws SQLException {
        String sql = "UPDATE soferi SET nume=?, telefon=?, email=?, numar_inmatriculare=?, este_disponibil=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, driver.getNume());
            stmt.setString(2, driver.getTelefon());
            stmt.setString(3, driver.getEmail());
            stmt.setString(4, driver.getNumarInmatriculare());
            stmt.setBoolean(5, driver.isDisponibil());
            stmt.setString(6, driver.getId());
            stmt.executeUpdate();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM soferi WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    private Driver mapRow(ResultSet rs) throws SQLException {
        Driver d = new Driver(rs.getString("nume"), rs.getString("telefon"), rs.getString("email"), rs.getString("numar_inmatriculare"));
        d.setId(rs.getString("id"));
        d.setDisponibil(rs.getBoolean("este_disponibil"));
        return d;
    }
}