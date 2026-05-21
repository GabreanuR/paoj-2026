package com.pao.proiect.fooddelivery;

import com.pao.proiect.fooddelivery.config.DatabaseConnection;
import com.pao.proiect.fooddelivery.model.*;
import com.pao.proiect.fooddelivery.repository.*;
import com.pao.proiect.fooddelivery.service.AuditService;
import com.pao.proiect.fooddelivery.service.DeliveryService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        AuditService audit = AuditService.getInstance();
        ClientRepository clientRepo = new ClientRepository();
        DriverRepository driverRepo = new DriverRepository();
        RestaurantRepository restRepo = new RestaurantRepository();
        ReviewRepository reviewRepo = new ReviewRepository();
        DeliveryService deliveryService = DeliveryService.getInstance();

        System.out.println("=== ETAPA 2: FOOD DELIVERY CU BAZĂ DE DATE ===");
        curataBazaDeDate();

        Address adresa1 = new Address("Strada Academiei 14", "București", "010014");
        Address adresa2 = new Address("Bulevardul Unirii 10", "București", "030167");

        Client client1 = new Client("Răzvan", "0711111111", "razvan@email.com", adresa1);
        Client client2 = new Client("Andrei", "0722222222", "andrei@email.com", adresa2);
        Driver sofer1 = new Driver("Mihai", "0733333333", "mihai@livrari.ro", "B-99-LIV");

        Restaurant rest1 = new Restaurant("Burger Place", adresa2);
        Restaurant rest2 = new Restaurant("Pizza OK", adresa1);

        MenuItem burger = new MenuItem("Cheeseburger", 35.0);
        MenuItem cartofi = new MenuItem("Cartofi prăjiți", 10.0);

        System.out.println("\n--- 1. POPULAREA SISTEMULUI ---");

        // Acțiunea 1: Adaugă clienți
        clientRepo.save(client1);
        clientRepo.save(client2);
        audit.log("adauga_clienti");

        // Acțiunea 2: Adaugă șofer
        driverRepo.save(sofer1);
        audit.log("adauga_sofer");

        // Acțiunea 3: Adaugă restaurante
        restRepo.save(rest1);
        restRepo.save(rest2);
        audit.log("adauga_restaurante");

        // Acțiunea 4: Adaugă produse
        salveazaProdusInDB(burger, rest1.getId());
        salveazaProdusInDB(cartofi, rest1.getId());
        audit.log("adauga_produse");

        System.out.println("\n--- 2. CĂUTARE ȘI ȘTERGERE ---");

        // Acțiunea 5: Caută client (în loc de excepție, folosim Optional cerut de prof)
        System.out.println("Căutăm clientul Răzvan în BD...");
        clientRepo.findById(client1.getId()).ifPresentOrElse(
                c -> System.out.println("Găsit: " + c.getNume() + " | Email: " + c.getEmail()),
                () -> System.out.println("Client negăsit.")
        );
        audit.log("cauta_client");

        // Acțiunea 6: Șterge client (Îl ștergem pe Andrei)
        clientRepo.delete(client2.getId());
        audit.log("sterge_client");

        System.out.println("\n--- 3. FLUX DE COMANDĂ (TRANZACȚIE JDBC) ---");

        List<MenuItem> produseComandate = new ArrayList<>();
        produseComandate.add(burger);
        produseComandate.add(cartofi);

        // Acțiunea 7: Plasează comanda folosind Tranzacția Explicită
        try {
            deliveryService.placeOrderWithTransaction(client1, rest1, produseComandate);
            audit.log("plaseaza_comanda_tranzactie");
        } catch (Exception e) {
            System.out.println("Comanda a picat: " + e.getMessage());
        }

        System.out.println("\n--- 4. RECENZII ȘI RATING ---");

        // Acțiunea 8: Adaugă recenzie
        Review recenzie = new Review(client1, rest1, 5, "Cei mai buni burgeri, livrare rapidă!");
        reviewRepo.save(recenzie);
        audit.log("adauga_recenzie");

        System.out.println("\n--- 5. RAPOARTE FINALE (JOIN-URI) ---");

        // Acțiunea 9: Afișează comenzile complete (JOIN)
        System.out.println("Istoric Comenzi Sistem:");
        deliveryService.getComenziComplete().forEach(System.out::println);
        audit.log("raport_comenzi_join");

        // Acțiunea 10: Afișează ratingurile reale (JOIN)
        System.out.println("\nTop Restaurante (calculate din BD):");
        deliveryService.getRestauranteCuRatingReal().forEach(System.out::println);
        audit.log("raport_restaurante_join");

        // Închidem conexiunea cu baza de date conform baremului
        System.out.println("\n=== DEMO FINALIZAT. Verifică fișierul audit.csv ===");
        DatabaseConnection.getInstance().close();
    }

    private static void salveazaProdusInDB(MenuItem produs, String idRestaurant) throws Exception {
        String sql = "INSERT INTO produse (id, id_restaurant, nume, pret) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produs.getId());
            ps.setString(2, idRestaurant);
            ps.setString(3, produs.getNume());
            ps.setDouble(4, produs.getPret());
            ps.executeUpdate();
            System.out.println("Produs adăugat în DB: " + produs.getNume());
        }
    }

    private static void curataBazaDeDate() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             java.sql.Statement stmt = conn.createStatement()) {

            stmt.execute("DELETE FROM recenzii");
            stmt.execute("DELETE FROM comenzi_produse");
            stmt.execute("DELETE FROM comenzi");
            stmt.execute("DELETE FROM produse");
            stmt.execute("DELETE FROM restaurante");
            stmt.execute("DELETE FROM soferi");
            stmt.execute("DELETE FROM clienti");

            System.out.println("[INFO] Baza de date a fost curățată pentru o nouă rulare de test.\n");
        } catch (Exception e) {
            System.err.println("Eroare la curățarea bazei de date: " + e.getMessage());
        }
    }
}