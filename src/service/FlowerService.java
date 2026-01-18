import java.sql.SQLException;
import java.util.List;

public class FlowerService {
    private FlowerRepository repo;

    public FlowerService(FlowerRepository repo) {
        this.repo = repo;
    }

    public void addFlower(String name, double price) throws SQLException {
        if (price == 0) {
            System.out.println("Price must be more than 0");
            return;
        }
        repo.addFlower(name, price);
    }

    public List<Flower> listFlowers() throws SQLException {
        return repo.getALLFlowers();
    }
}
