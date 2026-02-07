package controllers;

import entity.Buyer;
import entity.Category;
import entity.Flower;
import entity.Role;
import factory.UserFactory;
import repositories.CategoryRepository;
import service.FlowerService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class FlowerController {
    private static final String ADMIN_PASSWORD = "1234";
    private final FlowerService service;
    private final CategoryRepository categoryRepo;
    private final Scanner scanner = new Scanner(System.in);
    private final Buyer currentUser;

    public FlowerController(FlowerService service, CategoryRepository categoryRepo) {
        this.service = service;
        this.categoryRepo = categoryRepo;

        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Role (1=ADMIN, 2=BUYER): ");
        int r = readIntSafe();

        Role role;
        if (r == 1) {
            System.out.print("Enter admin password: ");
            String pass = scanner.nextLine().trim();

            if (ADMIN_PASSWORD.equals(pass)) {
                role = Role.ADMIN;
                System.out.println(" Admin access granted.");
            } else {
                role = Role.BUYER;
                System.out.println(" Wrong password. You are logged in as BUYER.");
            }
        } else {
            role = Role.BUYER;
        }

        this.currentUser = UserFactory.createBuyer(name, role);

        System.out.println(" Logged in as: " + currentUser.getName() + " (" + currentUser.getRole() + ")");
    }

    public void start() {
        while (true) {
            System.out.println("1. Add flower (ADMIN only)");
            System.out.println("2. Show all flowers");
            System.out.println("3. Buy flower");
            System.out.println("4. Delete flower (ADMIN only)");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int cmd = readIntSafe();

            try {
                if (cmd == 1) addFlower();
                else if (cmd == 2) showFlowers();
                else if (cmd == 3) buyFlower();
                else if (cmd == 4) deleteFlower();
                else if (cmd == 0) break;
                else System.out.println("Unknown command.");
            } catch (SQLException e) {
                System.out.println(" DB error: " + e.getMessage());
            }
        }
    }

    private void addFlower() throws SQLException {
        if (!isAdmin()) {
            System.out.println(" Access denied. Only ADMIN can add flowers.");
            return;
        }

        System.out.print("Flower name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Price: ");
        double price = readDoubleSafe();

        System.out.print("Stock: ");
        int stock = readIntSafe();

        Category category = chooseCategory();
        if (category == null) {
            System.out.println("Category not selected.");
            return;
        }

        service.createFlower(new Flower(name, price, stock, category));
    }

    private void showFlowers() throws SQLException {
        List<Flower> flowers = service.listFlowers();

        if (flowers.isEmpty()) {
            System.out.println("No flowers found.");
            return;
        }

        flowers.forEach(System.out::println);
    }

    private void buyFlower() throws SQLException {
        showFlowers();

        System.out.print("Enter flower ID: ");
        int id = readIntSafe();

        System.out.print("Enter quantity: ");
        int qty = readIntSafe();

        double total = service.buyFlower(currentUser, id, qty);
        if (total > 0) {
            System.out.println("Total price: " + total + " tg");
        }
    }

    private void deleteFlower() throws SQLException {
        if (!isAdmin()) {
            System.out.println(" Access denied. Only ADMIN can delete flowers.");
            return;
        }

        showFlowers();

        System.out.print("Enter flower ID to delete: ");
        int id = readIntSafe();

        service.deleteFlower(id);
    }

    private Category chooseCategory() throws SQLException {
        List<Category> categories = categoryRepo.getAll();
        if (categories.isEmpty()) {
            System.out.println("No categories in DB. Add categories first.");
            return null;
        }

        System.out.println("Available categories:");
        categories.forEach(System.out::println);

        System.out.print("Choose category ID: ");
        int catId = readIntSafe();

        return categoryRepo.getById(catId);
    }

    private boolean isAdmin() {
        return currentUser.getRole() == Role.ADMIN;
    }

    private int readIntSafe() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid integer: ");
            scanner.nextLine();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private double readDoubleSafe() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Enter a valid number: ");
            scanner.nextLine();
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }
}
