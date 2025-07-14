package carsharing.dao;

import carsharing.dbClient.DbClient;
import carsharing.entities.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public class CustomerDaoImpl implements CustomerDao {
    private final DbClient dbClient;
    private final String createTableSql = """
                        create table if not exists CUSTOMER (
                            ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                            NAME VARCHAR(255) UNIQUE NOT NULL,
                            RENTED_CAR_ID INT,
                            CONSTRAINT FK_Car FOREIGN KEY (RENTED_CAR_ID)
                                            REFERENCES CAR(ID)
            )
            """;

    public CustomerDaoImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }


    @Override
    public void createTable() {
        this.dbClient.executeUpdate(this.createTableSql);
    }

    @Override
    public List<Customer> getAll() {
        String sql = """
                SELECT * from CUSTOMER
                """;
        ResultSet rs = this.dbClient.executeQuery(sql);
        return this.convertRsToList(rs);
    }

    @Override
    public boolean insert(Customer record) {
        String sql = """
                INSERT INTO CUSTOMER(name)
                VALUES (?)
                """;
        try (PreparedStatement stmt = dbClient.getConnection().prepareStatement(sql)) {
            stmt.setString(1, record.name());
            return stmt.execute();
        } catch (SQLException e) {
            return false;
        }
    }

    private List<Customer> convertRsToList(ResultSet rs) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<Customer>(
                Long.MAX_VALUE, Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer<? super Customer> action) {
                try {
                    if (!rs.next()) return false;
                    action.accept(createCustomerRecord(rs));
                    return true;
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }, false).toList();
    }

    private Customer createCustomerRecord(ResultSet rs) throws SQLException {
        return new Customer(rs.getInt("ID"), rs.getString("name"), rs.getInt("rented_car_id"));
    }
}
