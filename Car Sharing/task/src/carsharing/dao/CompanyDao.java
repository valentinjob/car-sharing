package carsharing.dao;

import carsharing.dbClient.DbClient;
import carsharing.entities.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public class CompanyDao implements DAO<Company> {
    private final DbClient dbClient;

    private final String createTableSql = """
                        create table if not exists COMPANY (
                            ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                            NAME VARCHAR(255) UNIQUE NOT NULL
            )
            """;

    public CompanyDao(DbClient client) {
        this.dbClient = client;
    }

    @Override
    public void createTable() {
        this.dbClient.executeUpdate(createTableSql);
    }

    @Override
    public List<Company> getAll() {
        String sql = """
                SELECT * from COMPANY
                """;
        ResultSet rs = this.dbClient.executeQuery(sql);

        return StreamSupport.stream(new Spliterators.AbstractSpliterator<Company>(
                Long.MAX_VALUE, Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer<? super Company> action) {
                try {
                    if (!rs.next()) return false;
                    action.accept(createCompanyRecord(rs));
                    return true;
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }, false).toList();
    }

    @Override
    public boolean insert(Company record) {
        String sql = """
                INSERT INTO COMPANY(name)
                VALUES (?)
                """;
        try (PreparedStatement stmt = dbClient.getConnection().prepareStatement(sql)) {
            stmt.setString(1, record.name());
            return stmt.execute();
        } catch (SQLException e) {
            return false;
        }
    }

    private Company createCompanyRecord(ResultSet rs) throws SQLException {
        return new Company(rs.getInt("ID"), rs.getString("name"));
    }
}
