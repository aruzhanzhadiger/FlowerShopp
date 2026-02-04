package repositories;

import entity.Flower;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlowerRepository {
    private Connection conn;

    public FlowerRepository(Connection conn) {
        this.conn = conn;
    }

    public void addFlower(Flower flower) throws SQLException {
        String sql = "INSERT INTO flowers(name, price, stock) VALUES(?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, flower.getName());
        ps.setDouble(2, flower.getPrice());
        ps.setInt(3, flower.getStock());
        ps.executeUpdate();
        ps.close();
    }

    public List<Flower> getALLFlowers() throws SQLException {
        List<Flower> flowers = new ArrayList<>();
        String sql = "SELECT id, name, price, stock FROM flowers";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price = rs.getDouble("price");
            int stock = rs.getInt("stock");
            flowers.add(new Flower(id, name, price, stock));
        }
        return flowers;
    }
    public Flower getFlowerById(int id) throws SQLException {
        String sql = "SELECT id, name, price, stock FROM flowers WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Flower(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
            );
        }
        return null;
    }

    public void updateStock(int id, int stock) throws SQLException {
        String sql = "UPDATE flowers SET stock = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, stock);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    public void deleteFlower(int id) throws SQLException {
        String sql = "DELETE FROM flowers WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
