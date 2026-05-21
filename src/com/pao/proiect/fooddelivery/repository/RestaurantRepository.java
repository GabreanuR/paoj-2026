package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.config.DatabaseConnection;
import com.pao.proiect.fooddelivery.model.Address;
import com.pao.proiect.fooddelivery.model.Restaurant;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantRepository implements Repository<Restaurant, String> {

    @Override
    public void save(Restaurant restaurant) throws SQLException {
        String sql = "INSERT INTO restaurante (id, nume, strada, oras, cod_postal, rating, numar_reviews) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getId());
            stmt.setString(2, restaurant.getNume());

            if (restaurant.getAdresa() != null) {
                stmt.setString(3, restaurant.getAdresa().getStrada());
                stmt.setString(4, restaurant.getAdresa().getOras());
                stmt.setString(5, restaurant.getAdresa().getCodPostal());
            } else {
                stmt.setNull(3, Types.VARCHAR); stmt.setNull(4, Types.VARCHAR); stmt.setNull(5, Types.VARCHAR);
            }
            stmt.setDouble(6, restaurant.getRating());
            stmt.setInt(7, restaurant.getNumarReviews());
            stmt.executeUpdate();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Restaurant> findById(String id) throws SQLException {
        String sql = "SELECT * FROM restaurante WHERE id = ?";
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
    public List<Restaurant> findAll() throws SQLException {
        List<Restaurant> restaurante = new ArrayList<>();
        String sql = "SELECT * FROM restaurante";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) restaurante.add(mapRow(rs));
        } catch (IOException e) { throw new RuntimeException(e); }
        return restaurante;
    }

    @Override
    public void update(Restaurant restaurant) throws SQLException {
        String sql = "UPDATE restaurante SET nume=?, strada=?, oras=?, cod_postal=?, rating=?, numar_reviews=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, restaurant.getNume());
            if (restaurant.getAdresa() != null) {
                stmt.setString(2, restaurant.getAdresa().getStrada());
                stmt.setString(3, restaurant.getAdresa().getOras());
                stmt.setString(4, restaurant.getAdresa().getCodPostal());
            } else {
                stmt.setNull(2, Types.VARCHAR); stmt.setNull(3, Types.VARCHAR); stmt.setNull(4, Types.VARCHAR);
            }
            stmt.setDouble(5, restaurant.getRating());
            stmt.setInt(6, restaurant.getNumarReviews());
            stmt.setString(7, restaurant.getId());
            stmt.executeUpdate();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM restaurante WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    private Restaurant mapRow(ResultSet rs) throws SQLException {
        Address adr = new Address(rs.getString("strada"), rs.getString("oras"), rs.getString("cod_postal"));
        return new Restaurant(
                rs.getString("id"),
                rs.getString("nume"),
                adr,
                rs.getDouble("rating"),
                rs.getInt("numar_reviews")
        );
    }
}