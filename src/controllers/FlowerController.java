import entity.Flower;

import java.security.Provider;
import java.sql.SQLException;
import java.util.Scanner;


public class FlowerController {
    private FlowerService service;
    private Scanner scanner = new Scanner(System.in);

    public FlowerController(FlowerService service) {
        this.service = service;
    }

    public void start() {
        while (true) {
            System.out.println("1. Add flower");
            System.out.println("2. Show all flowers");
            System.out.println("0. Exit");
            System.out.println("Choose: ");

            int cmd = scanner.nextInt();
            scanner.nextLine();

            try {
                if (cmd == 1) addFlower();
                else if (cmd == 2) showFlowers();
                else if (cmd == 0) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void addFlower() throws SQLException {
        System.out.print("Flower name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        service.createFlower(name, price);
    }

    private void showFlowers() throws SQLException {
        for (Flower f : service.listFlowers()) {
            System.out.println(f);
        }
    }
}
