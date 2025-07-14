package carsharing.dbClient;

import java.sql.*;

public class H2DbClient implements DbClient {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/carsharing/db/";

    private final Connection connection;

    public H2DbClient(String dbName) {
        try {
            Class.forName(JDBC_DRIVER);
            String fullDbUrl = DB_URL + dbName;

            this.connection = DriverManager.getConnection(fullDbUrl);
            this.connection.setAutoCommit(true);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void executeUpdate(String sql, Object... parameters) {
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalArgumentException("SQL statement cannot be null or empty");
        }
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                stmt.setObject(i + 1, parameters[i]);
            }
            stmt.executeUpdate();
        } catch (SQLSyntaxErrorException e) {
            throw new IllegalArgumentException("Invalid SQL syntax: " + e.getMessage(), e);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute update: " + e.getMessage(), e);
        }
    }


    @Override
    public ResultSet executeQuery(String sql) {
        Statement stmt;
        try {
            stmt = this.connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            this.connection.close();
        }
    }
}
