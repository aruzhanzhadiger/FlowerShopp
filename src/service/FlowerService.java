package service;

import entity.Buyer;
import entity.Flower;
import repositories.FlowerRepository;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class FlowerService {
    private final FlowerRepository repo;

    public FlowerService(FlowerRepository repo) {
        this.repo = repo;
    }

    public void createFlower(Flower flower) throws SQLException {
        if (flower == null) {
            System.out.println("Flower is null.");
            return;
        }
        if (flower.getName() == null || flower.getName().trim().length() < 2) {
            System.out.println("Name must be at least 2 characters.");
            return;
        }
        if (flower.getPrice() <= 0) {
            System.out.println("Price must be more than 0.");
            return;
        }
        if (flower.getStock() < 0) {
            System.out.println("Stock cannot be negative.");
            return;
        }
        if (flower.getCategory() == null || flower.getCategory().getId() <= 0) {
            System.out.println("Category must be selected.");
            return;
        }

        repo.addFlower(flower);
        System.out.println(" Flower added.");
    }

    public List<Flower> listFlowers() throws SQLException {
        return repo.getAllFlowers()
                .stream()
                .sorted(Comparator.comparing(Flower::getId))
                .toList();
    }

    public double buyFlower(Buyer buyer, int flowerId, int quantity) throws SQLException {
        if (quantity <= 0) {
            System.out.println("Quantity must be > 0");
            return 0;
        }

        Flower flower = repo.getFlowerById(flowerId);
        if (flower == null) {
            System.out.println("Flower not found.");
            return 0;
        }

        if (quantity > flower.getStock()) {
            System.out.println("Not enough flowers in stock.");
            return 0;
        }

        double total = flower.getPrice() * quantity;

        buyer.increasePurchases();
        if (buyer.getPurchases() % 11 == 0) {
            total *= 0.9;
            System.out.println(" You received a 10% discount for your 11th purchase!");
        }

        repo.updateStock(flowerId, flower.getStock() - quantity);
        System.out.println("Purchase successful.");
        return total;
    }

    public void deleteFlower(int id) throws SQLException {
        Flower flower = repo.getFlowerById(id);
        if (flower == null) {
            System.out.println("Flower with this ID does not exist.");
            return;
        }
        repo.deleteFlower(id);
        System.out.println("Flower deleted.");
    }
}
