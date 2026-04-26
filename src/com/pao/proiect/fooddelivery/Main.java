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
        // Obținem instanțele unice (Singleton) ale serviciilor noastre
        UserService userService = UserService.getInstance();
        OrderService orderService = OrderService.getInstance();

        System.out.println("=== 1. POPULAREA SISTEMULUI CU DATE ===");

        Address adresa1 = new Address("Strada Academiei 14", "București", "010014");
        Address adresa2 = new Address("Bulevardul Unirii 10", "București", "030167");

        Client client1 = new Client("Răzvan", "0711111111", "razvan@email.com", adresa1);
        Client client2 = new Client("Andrei", "0722222222", "andrei@email.com", adresa2);
        Driver sofer1 = new Driver("Mihai", "0733333333", "mihai@livrari.ro", "B-99-LIV");

        userService.addClient(client1);
        userService.addClient(client2);
        userService.addDriver(sofer1);

        userService.deleteClient("andrei@email.com");

        System.out.println();
        userService.afiseazaTotiClientii();
        System.out.println();

        Restaurant rest1 = new Restaurant("Burger Place", adresa2);
        Restaurant rest2 = new Restaurant("Pizza OK", adresa1);

        orderService.addRestaurant(rest1);
        orderService.addRestaurant(rest2);

        MenuItem burger = new MenuItem("Cheeseburger", 35.0);
        MenuItem cartofi = new MenuItem("Cartofi prăjiți", 10.0);
        MenuItem pizza = new MenuItem("Pizza Margherita", 40.0);

        // Adăugăm produsele direct în meniul restaurantului
        rest1.addMenuItem(burger);
        rest1.addMenuItem(cartofi);
        rest2.addMenuItem(pizza);

        System.out.println("\n=== 2. TESTARE EXCEPȚII (UserNotFoundException) ===");
        try {
            System.out.println("Căutăm clientul razvan@email.com...");
            Client gasit = userService.findClientByEmail("razvan@email.com");
            System.out.println("Găsit: " + gasit.getNume());

            System.out.println("Căutăm clientul inexistent@email.com...");
            userService.findClientByEmail("inexistent@email.com"); // Aici va crăpa intenționat
        } catch (UserNotFoundException e) {
            System.out.println("EXCEPȚIE PRINSĂ: " + e.getMessage());
        }

        System.out.println("\n=== 3. SIMULAREA UNUI FLUX DE COMANDĂ ===");

        List<MenuItem> produseComandate = new ArrayList<>();
        produseComandate.add(burger);
        produseComandate.add(cartofi);

        Order comanda1 = orderService.placeOrder(client1, rest1, produseComandate);

        // Testăm a doua excepție (NoAvailableDriverException)
        try {
            orderService.assignDriverToOrder(comanda1);
            comanda1.setStatus("IN_LIVRARE");
            System.out.println("Status comandă actualizat: " + comanda1.getStatus());

            // Facem șoferul indisponibil ca să testăm excepția la următoarea comandă
            comanda1.setStatus("FINALIZATA");
            if (comanda1.getSofer() != null) {
                comanda1.getSofer().setEsteDisponibil(true);
            }

        } catch (NoAvailableDriverException e) {
            System.out.println("EXCEPȚIE PRINSĂ: " + e.getMessage());
        }

        System.out.println("\n=== 4. RECENZII ȘI ACTUALIZARE RATING ===");

        orderService.addReview(client1, rest1, 5, "Cei mai buni burgeri, livrare rapidă!");

        System.out.println("\n=== 5. RAPOARTE FINALE (TreeSet) ===");

        orderService.getTopRestaurants();
    }
}