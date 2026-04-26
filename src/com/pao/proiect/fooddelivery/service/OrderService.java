package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.model.*;
import com.pao.proiect.fooddelivery.exception.NoAvailableDriverException;

import java.util.*;

public class OrderService {
    private static OrderService instance;

    private final List<Order> comenzi;
    private final List<Review> recenzii;
    private final Set<Restaurant> restaurante;

    private OrderService() {
        this.comenzi = new ArrayList<>();
        this.recenzii = new ArrayList<>();
        this.restaurante = new TreeSet<>();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurante.add(restaurant);
        System.out.println("Restaurant adăugat: " + restaurant.getNume());
    }

    public Order placeOrder(Client client, Restaurant restaurant, List<MenuItem> produse) {
        Order comanda = new Order(client, restaurant, produse);
        comenzi.add(comanda);
        System.out.println("Comandă plasată cu succes de " + client.getNume() + " la " + restaurant.getNume());
        return comanda;
    }

    public void assignDriverToOrder(Order comanda) {
        List<Driver> soferi = UserService.getInstance().getSoferi();
        for (Driver sofer : soferi) {
            if (sofer.isDisponibil()) {
                comanda.setSofer(sofer);
                sofer.setDisponibil(false);
                comanda.setStatus("IN_LIVRARE");
                System.out.println("Șoferul " + sofer.getNume() + " a preluat comanda.");
                return;
            }
        }
        throw new NoAvailableDriverException("Sistemul este aglomerat. Niciun șofer nu este disponibil!");
    }

    public void updateOrderStatus(Order comanda, String statusNou) {
        comanda.setStatus(statusNou);
        System.out.println("Statusul comenzii a fost actualizat la: " + statusNou);

        if (statusNou.equals("FINALIZATA") && comanda.getSofer() != null) {
            comanda.getSofer().setDisponibil(true);
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

    public void addReview(Client client, Restaurant restaurant, int nota, String mesaj) {
        Review review = new Review(client, restaurant, nota, mesaj);
        recenzii.add(review);
        restaurant.updateRating(nota);
        System.out.println("Review adăugat! Noul rating pt " + restaurant.getNume() + " este: " + restaurant.getRating());
    }

    public void getTopRestaurants() {
        System.out.println("--- Top Restaurante ---");
        for (Restaurant r : restaurante) {
            System.out.println(r);
        }
    }
}