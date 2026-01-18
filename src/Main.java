public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "0000"
            );
            FlowerRepository repo = new FlowerRepository(conn);
            repo.addFlower("Rose", 2000.0);
            repo.addFlower("Tulip", 1250);

            System.out.println("Flower in DB:");
            for (Flower f : repo.getALLFlowers()) {
                System.out.println(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}