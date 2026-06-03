package business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/school_db"),
                System.getenv().getOrDefault("DB_USER", "root"),
                System.getenv().getOrDefault("DB_PASSWORD", "")
        );
    }
}
