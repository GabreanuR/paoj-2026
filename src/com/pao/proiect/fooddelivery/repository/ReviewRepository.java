package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.config.DatabaseConnection;
import com.pao.proiect.fooddelivery.model.Client;
import com.pao.proiect.fooddelivery.model.Restaurant;
import com.pao.proiect.fooddelivery.model.Review;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewRepository implements Repository<Review, String> {

    @Override
    public void save(Review review) throws SQLException {
        String sql = "INSERT INTO recenzii (id, id_client, id_restaurant, nota, comentariu) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, review.getId());
            stmt.setString(2, review.getClient().getId());
            stmt.setString(3, review.getRestaurant().getId());
            stmt.setInt(4, review.getNota());
            stmt.setString(5, review.getComentariu());
            stmt.executeUpdate();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Review> findById(String id) throws SQLException {
        String sql = "SELECT * FROM recenzii WHERE id = ?";
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
    public List<Review> findAll() throws SQLException {
        List<Review> recenzii = new ArrayList<>();
        String sql = "SELECT * FROM recenzii";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) recenzii.add(mapRow(rs));
        } catch (IOException e) { throw new RuntimeException(e); }
        return recenzii;
    }

    @Override
    public void update(Review review) throws SQLException {
        String sql = "UPDATE recenzii SET nota=?, comentariu=? WHERE id=?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, review.getNota());
            stmt.setString(2, review.getComentariu());
            stmt.setString(3, review.getId());
            stmt.executeUpdate();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM recenzii WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    private Review mapRow(ResultSet rs) throws SQLException {
        Client dummyClient = new Client("", "", "", null);
        dummyClient.setId(rs.getString("id_client"));

        Restaurant dummyRestaurant = new Restaurant(rs.getString("id_restaurant"), "", null, 0.0, 0);

        return new Review(dummyClient, dummyRestaurant, rs.getInt("nota"), rs.getString("comentariu"));
    }
}