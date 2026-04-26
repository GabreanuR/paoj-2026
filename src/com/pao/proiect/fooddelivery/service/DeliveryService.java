package com.pao.proiect.fooddelivery.service;

import model.*;
import java.util.*;

public class DeliveryService {
    private final List<Client> clienti;
    private final List<Driver> soferi;
    private final List<Order> comenzi;
    private final List<Review> recenzii;

    private final Set<Restaurant> restaurante;

    public DeliveryService() {
        this.clienti = new ArrayList<>();
        this.soferi = new ArrayList<>();
        this.comenzi = new ArrayList<>();
        this.recenzii = new ArrayList<>();
        this.restaurante = new TreeSet<>();
    }

    public void addClient(Client client) {
        clienti.add(client);
        System.out.println("Client adăugat: " + client.getNume());
    }

    public void addDriver(Driver sofer) {
        soferi.add(sofer);
        System.out.println("Șofer adăugat: " + sofer.getNume());
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurante.add(restaurant);
        System.out.println("Restaurant adăugat: " + restaurant.getNume());
    }

    public void addMenuItemToRestaurant(Restaurant restaurant, MenuItem item) {
        restaurant.addMenuItem(item);
        System.out.println("Produsul " + item.getNume() + " a fost adăugat la " + restaurant.getNume());
    }

    public Order placeOrder(Client client, Restaurant restaurant, List<MenuItem> produse) {
        Order comanda = new Order(client, restaurant, produse);
        comenzi.add(comanda);
        System.out.println("Comandă plasată cu succes de " + client.getNume() + " la " + restaurant.getNume());
        return comanda;
    }

    public void assignDriverToOrder(Order comanda) {
        for (Driver sofer : soferi) {
            if (sofer.isEsteDisponibil()) {
                comanda.setSofer(sofer);
                sofer.setEsteDisponibil(false);
                comanda.setStatus("IN_PREPARARE");
                System.out.println("Șoferul " + sofer.getNume() + " a fost alocat comenzii " + comanda.getId().substring(0,8));
                return;
            }
        }
        System.out.println("Nu există șoferi disponibili momentan!");
    }

    public void updateOrderStatus(Order comanda, String statusNou) {
        comanda.setStatus(statusNou);
        System.out.println("Statusul comenzii a fost actualizat la: " + statusNou);

        if (statusNou.equals("FINALIZATA") && comanda.getSofer() != null) {
            comanda.getSofer().setEsteDisponibil(true);
        }
    }

    public void getClientOrderHistory(Client client) {
        System.out.println("--- Istoric comenzi pentru " + client.getNume() + " ---");
        boolean hasOrders = false;
        for (Order comanda : comenzi) {
            if (comanda.getClient().getId().equals(client.getId())) {
                System.out.println(comanda);
                hasOrders = true;
            }
        }
        if (!hasOrders) {
            System.out.println("Clientul nu are nicio comandă.");
        }
    }

    public void getTopRestaurants() {
        System.out.println("--- Top Restaurante ---");
        for (Restaurant r : restaurante) {
            System.out.println(r);
        }
    }

    public void addReview(Client client, Restaurant restaurant, int nota, String comentariu) {
        Review review = new Review(client, restaurant, nota, comentariu);
        recenzii.add(review);
        restaurant.updateRating(nota);
        System.out.println("Review adăugat de " + client.getNume() + " pentru " + restaurant.getNume() + ". Noul rating: " + restaurant.getRating());
    }

    public void afiseazaTotiClientii() {
        System.out.println("--- Lista clienților înregistrați ---");
        if (clienti.isEmpty()) {
            System.out.println("Nu există clienți în sistem.");
            return;
        }
        for (Client client : clienti) {
            System.out.println(client);
        }
    }

    public void getRecenziiPentruRestaurant(Restaurant restaurant) {
        System.out.println("--- Recenzii pentru " + restaurant.getNume() + " ---");
        boolean areRecenzii = false;
        for (Review review : recenzii) {
            if (review.getRestaurant().getId().equals(restaurant.getId())) {
                System.out.println(review);
                areRecenzii = true;
            }
        }
        if (!areRecenzii) {
            System.out.println("Acest restaurant nu are încă nicio recenzie.");
        }
    }
}