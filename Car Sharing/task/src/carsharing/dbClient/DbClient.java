package carsharing.dbClient;

import java.sql.Connection;
import java.sql.ResultSet;

public interface DbClient extends AutoCloseable {
    void executeUpdate(String sql, Object... parameters);

    ResultSet executeQuery(String sql);

    Connection getConnection();
}
