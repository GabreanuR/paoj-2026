package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.config.DatabaseConnection;
import com.pao.proiect.fooddelivery.model.Address;
import com.pao.proiect.fooddelivery.model.Client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements Repository<Client, String> {

    @Override
    public void save(Client client) throws SQLException {
        String sql = "INSERT INTO clienti (id, nume, telefon, email, strada, oras, cod_postal) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getId());
            stmt.setString(2, client.getNume());
            stmt.setString(3, client.getTelefon());
            stmt.setString(4, client.getEmail());

            if (client.getAdresaLivrare() != null) {
                stmt.setString(5, client.getAdresaLivrare().getStrada());
                stmt.setString(6, client.getAdresaLivrare().getOras());
                stmt.setString(7, client.getAdresaLivrare().getCodPostal());
            } else {
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setNull(6, java.sql.Types.VARCHAR);
                stmt.setNull(7, java.sql.Types.VARCHAR);
            }

            stmt.executeUpdate();
            System.out.println("Client salvat în baza de date: " + client.getNume());
        } catch (IOException e) {
            throw new RuntimeException("Eroare la obținerea conexiunii: " + e.getMessage());
        }
    }

    @Override
    public Optional<Client> findById(String id) throws SQLException {
        String sql = "SELECT * FROM clienti WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToClient(rs));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Eroare la obținerea conexiunii: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() throws SQLException {
        List<Client> clienti = new ArrayList<>();
        String sql = "SELECT * FROM clienti";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clienti.add(mapResultSetToClient(rs));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return clienti;
    }

    @Override
    public void update(Client client) throws SQLException {
        String sql = "UPDATE clienti SET nume = ?, telefon = ?, email = ?, strada = ?, oras = ?, cod_postal = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, client.getNume());
            stmt.setString(2, client.getTelefon());
            stmt.setString(3, client.getEmail());

            if (client.getAdresaLivrare() != null) {
                stmt.setString(4, client.getAdresaLivrare().getStrada());
                stmt.setString(5, client.getAdresaLivrare().getOras());
                stmt.setString(6, client.getAdresaLivrare().getCodPostal());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setNull(6, java.sql.Types.VARCHAR);
            }
            stmt.setString(7, client.getId());

            stmt.executeUpdate();
            System.out.println("Client actualizat în baza de date: " + client.getNume());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM clienti WHERE id = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
            System.out.println("Client șters din baza de date (ID: " + id + ")");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Client mapResultSetToClient(ResultSet rs) throws SQLException {
        String strada = rs.getString("strada");
        String oras = rs.getString("oras");
        String codPostal = rs.getString("cod_postal");

        Address adresa = null;
        if (strada != null && oras != null) {
            adresa = new Address(strada, oras, codPostal);
        }

        Client client = new Client(
                rs.getString("nume"),
                rs.getString("telefon"),
                rs.getString("email"),
                adresa
        );

        client.setId(rs.getString("id"));
        return client;
    }
}