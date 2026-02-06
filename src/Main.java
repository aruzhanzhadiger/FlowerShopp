import controllers.FlowerController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.CategoryRepository;
import repositories.FlowerRepository;
import service.FlowerService;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            IDB db = PostgresDB.getInstance(
                    "jdbc:postgresql://localhost:5432",
                    "postgres",
                    "0000",
                    "FlowerShop"
            );

            Connection conn = db.getConnection();

            FlowerRepository flowerRepo = new FlowerRepository(conn);
            CategoryRepository categoryRepo = new CategoryRepository(conn);

            FlowerService service = new FlowerService(flowerRepo);
            FlowerController controller = new FlowerController(service, categoryRepo);

            controller.start();

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}import controllers.FlowerController;
import data.PostgresDB;
import data.interfaces.IDB;
import repositories.CategoryRepository;
import repositories.FlowerRepository;
import service.FlowerService;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            IDB db = PostgresDB.getInstance(
                    "jdbc:postgresql://localhost:5432",
                    "postgres",
                    "0000",
                    "FlowerShop"
            );

            Connection conn = db.getConnection();

            FlowerRepository flowerRepo = new FlowerRepository(conn);
            CategoryRepository categoryRepo = new CategoryRepository(conn);

            FlowerService service = new FlowerService(flowerRepo);
            FlowerController controller = new FlowerController(service, categoryRepo);

            controller.start();

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
