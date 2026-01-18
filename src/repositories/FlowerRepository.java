
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlowerRepository {
    private Connection conn;

    public FlowerRepository(Connection conn) {
        this.conn = conn;
    }

    public void addFlower(String name, double price) throws SQLException {
        String sql = "INSERT INTO flowers(name, price) VALUES(?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setDouble(2, price);
        ps.executeUpdate();
        ps.close();
    }

    public List<Flower> getALLFlowers() throws SQLException {
        List<Flower> flowers = new ArrayList<>();
        String sql = "SELECT id, name, price FROM flowers";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            flowers.add(new Flower(id, name, price));
        }
        return flowers;
    }
}