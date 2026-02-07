package data;

import data.interfaces.IDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {
    private static PostgresDB instance;
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    private PostgresDB(String hostUrl, String username, String password, String dbName) {
        this.url = hostUrl + "/" + dbName;
        this.username = username;
        this.password = password;
    }

    public static synchronized PostgresDB getInstance(String hostUrl, String username, String password, String dbName) {
        if (instance == null) {
            instance = new PostgresDB(hostUrl, username, password, dbName);
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) return connection;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found. Add postgresql jar to project libraries.", e);
        }

        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) connection.close();
    }
}
