package repositories;

import entity.Category;
import entity.Flower;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlowerRepository {
    private final Connection conn;

    public FlowerRepository(Connection conn) {
        this.conn = conn;
    }

    public void addFlower(Flower flower) throws SQLException {
        String sql = "INSERT INTO flowers(name, price, stock, category_id) VALUES(?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, flower.getName());
            ps.setDouble(2, flower.getPrice());
            ps.setInt(3, flower.getStock());
            ps.setInt(4, flower.getCategory().getId());
            ps.executeUpdate();
        }
    }

    public List<Flower> getAllFlowers() throws SQLException {
        List<Flower> flowers = new ArrayList<>();
        String sql = """
            SELECT f.id, f.name, f.price, f.stock,
                   c.id AS category_id, c.name AS category_name
            FROM flowers f
            JOIN categories c ON f.category_id = c.id
            ORDER BY f.id
        """;

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Category cat = new Category(rs.getInt("category_id"), rs.getString("category_name"));
                flowers.add(new Flower(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        cat
                ));
            }
        }
        return flowers;
    }

    public Flower getFlowerById(int id) throws SQLException {
        String sql = """
            SELECT f.id, f.name, f.price, f.stock,
                   c.id AS category_id, c.name AS category_name
            FROM flowers f
            JOIN categories c ON f.category_id = c.id
            WHERE f.id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category cat = new Category(rs.getInt("category_id"), rs.getString("category_name"));
                    return new Flower(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("stock"),
                            cat
                    );
                }
            }
        }
        return null;
    }

    public void updateStock(int id, int stock) throws SQLException {
        String sql = "UPDATE flowers SET stock = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stock);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public void deleteFlower(int id) throws SQLException {
        conn.setAutoCommit(false);
        try {
            try (PreparedStatement ps1 = conn.prepareStatement("DELETE FROM order_items WHERE flower_id = ?")) {
                ps1.setInt(1, id);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement("DELETE FROM flowers WHERE id = ?")) {
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}

