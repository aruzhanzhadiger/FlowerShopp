package controllers;

import entity.Buyer;
import entity.Flower;
import service.FlowerService;

import java.security.Provider;
import java.sql.SQLException;
import java.util.Scanner;


public class FlowerController {
    private FlowerService service;
    private Scanner scanner = new Scanner(System.in);
    private Buyer buyer;

    public FlowerController(FlowerService service) {
        this.service = service;
        System.out.print("Enter buyer name: ");
        this.buyer = new Buyer(scanner.nextLine());
    }

    public void start() {
        while (true) {
            System.out.println("1. Add flower");
            System.out.println("2. Show all flowers");
            System.out.println("3. Buy flower");
            System.out.println("4. Delete flower");
            System.out.println("0. Exit");
            System.out.println("Choose: ");

            int cmd = scanner.nextInt();
            scanner.nextLine();

            try {
                if (cmd == 1) addFlower();
                else if (cmd == 2) showFlowers();
                else if (cmd == 3) buyFlower();
                else if (cmd == 4) deleteFlower();
                else if (cmd == 0) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addFlower() throws SQLException {
        scanner.nextLine();
        System.out.print("Flower name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        service.createFlower(new Flower(name, price, stock));
    }

    private void showFlowers() throws SQLException {
        for (Flower f : service.listFlowers()) {
            System.out.println(f);
        }
    }

    private void buyFlower() throws SQLException {
        showFlowers();

        System.out.print("Enter flower ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter quantity: ");
        int qty = scanner.nextInt();

        double total = service.buyFlower(buyer, id, qty);
        if (total > 0) {
            System.out.println("Total price: " + total + " tg");
        }
    }

    private void deleteFlower() throws SQLException {
        showFlowers();
        System.out.print("Enter flower ID to delete: ");
        int id = scanner.nextInt();
        service.deleteFlower(id);
        System.out.println("Flower deleted successfully");
    }
}

