package com.pao.proiect.fooddelivery;

import com.pao.proiect.fooddelivery.model.*;
import com.pao.proiect.fooddelivery.service.OrderService;
import com.pao.proiect.fooddelivery.service.UserService;
import com.pao.proiect.fooddelivery.exception.NoAvailableDriverException;
import com.pao.proiect.fooddelivery.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        OrderService orderService = OrderService.getInstance();

        System.out.println("=== 1. POPULAREA SISTEMULUI (Acțiunile 1, 2, 3, 4) ===");

        Address adresa1 = new Address("Strada Academiei 14", "București", "010014");
        Address adresa2 = new Address("Bulevardul Unirii 10", "București", "030167");

        Client client1 = new Client("Răzvan", "0711111111", "razvan@email.com", adresa1);
        Client client2 = new Client("Andrei", "0722222222", "andrei@email.com", adresa2);
        Driver sofer1 = new Driver("Mihai", "0733333333", "mihai@livrari.ro", "B-99-LIV");

        // Acțiunea 1: Adaugă client
        userService.addClient(client1);
        userService.addClient(client2);

        // Acțiunea 2: Adaugă șofer
        userService.addDriver(sofer1);
        System.out.println();
        userService.afiseazaTotiClientii();
        System.out.println();

        Restaurant rest1 = new Restaurant("Burger Place", adresa2);
        Restaurant rest2 = new Restaurant("Pizza OK", adresa1);

        // Acțiunea 3: Adaugă restaurant
        orderService.addRestaurant(rest1);
        orderService.addRestaurant(rest2);

        MenuItem burger = new MenuItem("Cheeseburger", 35.0);
        MenuItem cartofi = new MenuItem("Cartofi prăjiți", 10.0);
        MenuItem pizza = new MenuItem("Pizza Margherita", 40.0);

        // Acțiunea 4: Adaugă preparate în meniu
        rest1.addMenuItem(burger);
        rest1.addMenuItem(cartofi);
        rest2.addMenuItem(pizza);

        System.out.println("\n=== 2. CĂUTARE ȘI ȘTERGERE (Acțiunile 9, 10) ===");
        try {
            // Acțiunea 9: Caută client (cu tratare excepție)
            System.out.println("Căutăm clientul razvan@email.com...");
            Client gasit = userService.findClientByEmail("razvan@email.com");
            System.out.println("Găsit: " + gasit.getNume());

            System.out.println("Căutăm un client inexistent...");
            userService.findClientByEmail("inexistent@email.com");
        } catch (UserNotFoundException e) {
            System.out.println("EXCEPȚIE PRINSĂ: " + e.getMessage());
        }

        System.out.println();
        // Acțiunea 10: Șterge client
        userService.deleteClient("andrei@email.com");

        System.out.println("\n=== 3. FLUX DE COMANDĂ (Acțiunile 5, 6, 7) ===");

        List<MenuItem> produseComandate = new ArrayList<>();
        produseComandate.add(burger);
        produseComandate.add(cartofi);

        // Acțiunea 5: Plasează comanda
        Order comanda1 = orderService.placeOrder(client1, rest1, produseComandate);

        try {
            // Acțiunea 6: Alocă șofer
            orderService.assignDriverToOrder(comanda1);

            // Acțiunea 7: Actualizează statusul
            orderService.updateOrderStatus(comanda1, "IN_LIVRARE");
            orderService.updateOrderStatus(comanda1, "FINALIZATA");

        } catch (NoAvailableDriverException e) {
            System.out.println("EXCEPȚIE PRINSĂ: " + e.getMessage());
        }

        System.out.println("\n=== 4. RECENZII ȘI RATING (Acțiunea 8) ===");

        // Acțiunea 8: Adaugă recenzie
        orderService.addReview(client1, rest1, 5, "Cei mai buni burgeri, livrare rapidă!");

        System.out.println("\n=== 5. RAPOARTE FINALE (Metodele care apăreau unused) ===");

        orderService.getClientOrderHistory(client1);
        System.out.println();

        orderService.getRecenziiPentruRestaurant(rest1);
        System.out.println();

        orderService.getTopRestaurants();
    }
}