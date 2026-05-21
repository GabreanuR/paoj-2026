package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.config.DatabaseConnection;
import com.pao.proiect.fooddelivery.model.Client;
import com.pao.proiect.fooddelivery.model.MenuItem;
import com.pao.proiect.fooddelivery.model.Restaurant;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeliveryService {

    private static DeliveryService instance;

    private DeliveryService() {}

    public static DeliveryService getInstance() {
        if (instance == null) instance = new DeliveryService();
        return instance;
    }

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    // =========================================================
    // RÂND BAREM 5 — Tranzacție explicită (COMMIT / ROLLBACK)
    // =========================================================

    public String placeOrderWithTransaction(Client client, Restaurant restaurant, List<MenuItem> produse) throws SQLException, IOException {
        Connection conn = getConn();
        conn.setAutoCommit(false);

        String orderId = UUID.randomUUID().toString();

        try {
            double pretTotal = 0;
            for (MenuItem item : produse) {
                pretTotal += item.getPret();
            }

            String insertOrderSql = "INSERT INTO comenzi (id, id_client, id_restaurant, pret_total, status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement psOrder = conn.prepareStatement(insertOrderSql)) {
                psOrder.setString(1, orderId);
                psOrder.setString(2, client.getId());
                psOrder.setString(3, restaurant.getId());
                psOrder.setDouble(4, pretTotal);
                psOrder.setString(5, "PLASATA");
                psOrder.executeUpdate();
            }

            String insertProductSql = "INSERT INTO comenzi_produse (id_comanda, id_produs) VALUES (?, ?)";
            try (PreparedStatement psProdus = conn.prepareStatement(insertProductSql)) {
                for (MenuItem produs : produse) {
                    psProdus.setString(1, orderId);
                    psProdus.setString(2, produs.getId());
                    psProdus.executeUpdate();
                }
            }

            conn.commit();
            System.out.println("[TRANZACȚIE] Comandă plasată cu succes! ID: " + orderId);
            return orderId;

        } catch (SQLException e) {
            conn.rollback();
            System.err.println("[ROLLBACK] Eroare la plasarea comenzii. Toate datele au fost anulate: " + e.getMessage());
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // =========================================================
    // RÂND BAREM 6 — Cele 3 interogări SQL cu JOIN
    // =========================================================

    public List<String> getComenziComplete() throws SQLException, IOException {
        String sql = """
            SELECT c.id as order_id, 
                   cl.nume as nume_client, 
                   r.nume as nume_restaurant, 
                   c.pret_total, 
                   c.status
            FROM comenzi c
            JOIN clienti cl ON c.id_client = cl.id
            JOIN restaurante r ON c.id_restaurant = r.id
            ORDER BY c.pret_total DESC
            """;

        List<String> rezultate = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rezultate.add(String.format("Comanda [%s] | Client: %s | Restaurant: %s | Total: %.2f RON | Status: %s",
                        rs.getString("order_id").substring(0, 8),
                        rs.getString("nume_client"),
                        rs.getString("nume_restaurant"),
                        rs.getDouble("pret_total"),
                        rs.getString("status")));
            }
        }
        return rezultate;
    }

    public List<String> getRestauranteCuRatingReal() throws SQLException, IOException {
        String sql = """
            SELECT r.nume as nume_restaurant, 
                   AVG(rec.nota) as rating_mediu, 
                   COUNT(rec.id) as numar_recenzii
            FROM restaurante r
            LEFT JOIN recenzii rec ON r.id = rec.id_restaurant
            GROUP BY r.id, r.nume
            ORDER BY rating_mediu DESC
            """;

        List<String> rezultate = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                double rating = rs.getDouble("rating_mediu");
                int numar = rs.getInt("numar_recenzii");
                String afisareRating = (numar == 0) ? "Fără recenzii" : String.format("%.2f", rating);

                rezultate.add(String.format("Restaurant: %s | Rating mediu: %s din %d recenzii",
                        rs.getString("nume_restaurant"), afisareRating, numar));
            }
        }
        return rezultate;
    }

    public List<String> getCeleMaiComandateProduse() throws SQLException, IOException {
        String sql = """
            SELECT p.nume as nume_produs, 
                   r.nume as nume_restaurant, 
                   COUNT(cp.id_produs) as numar_comenzi
            FROM produse p
            JOIN restaurante r ON p.id_restaurant = r.id
            LEFT JOIN comenzi_produse cp ON p.id = cp.id_produs
            GROUP BY p.id, p.nume, r.nume
            ORDER BY numar_comenzi DESC
            LIMIT 5
            """;

        List<String> rezultate = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rezultate.add(String.format("Produs: '%s' (de la %s) - comandat de %d ori",
                        rs.getString("nume_produs"),
                        rs.getString("nume_restaurant"),
                        rs.getInt("numar_comenzi")));
            }
        }
        return rezultate;
    }
}