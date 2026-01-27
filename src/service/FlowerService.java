package service;

import entity.Flower;
import repositories.FlowerRepository;

import java.sql.SQLException;
import java.util.List;

public class FlowerService {
    private FlowerRepository repo;

    public FlowerService(FlowerRepository repo) {
        this.repo = repo;
    }

    public void createFlower(Flower flower) throws SQLException {
        if (flower.getPrice() == 0) {
            System.out.println("Price must be more than 0");
            return;
        }
        repo.addFlower(flower);
    }

    public List<Flower> listFlowers() throws SQLException {
        return repo.getALLFlowers();
    }
}
