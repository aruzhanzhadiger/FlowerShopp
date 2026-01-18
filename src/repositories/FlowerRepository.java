
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class FlowerRepository {
    private Connection conn;

    public FlowerRepository(Connection conn) {
        this.conn = conn;
    }

    public void addFlower(String name, int price)  {
        try {
            String sql = "INSERT INTO flowers(name, price) VALUES(?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, price);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    public List<Flower> getALLFlowers()  {
        List<Flower> list = new ArrayList<>();

        try {
        String sql = "SELECT id, name, price FROM flowers";
        Statement stmt = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            list.add(new Flower(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("price")
                ));
        }
        rs.close();
        stmt.close();

 }    catch (Exception e) {
        System.out.println("Error" + e.getMessage());
        }
        return list;
    }
}
