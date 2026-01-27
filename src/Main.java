import controllers.FlowerController;
import data.PostgresDB;
import data.interfaces.IDB;
import entity.Flower;
import repositories.FlowerRepository;
import service.FlowerService;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        try {
            IDB db = new PostgresDB("jdbc:postgresql://localhost:5432", "postgres", "0000", "FlowerShop");

            Connection conn = db.getConnection();

            FlowerRepository repo = new FlowerRepository(conn);
            FlowerService service = new FlowerService(repo);
            FlowerController controller = new FlowerController(service);

            controller.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
