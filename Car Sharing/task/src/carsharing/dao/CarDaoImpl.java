package carsharing.dao;

import carsharing.dbClient.DbClient;
import carsharing.entities.Car;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public class CarDaoImpl implements CarDao {

    private final DbClient dbClient;

    private final String createTableSql = """
                        create table if not exists CAR (
                            ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                            NAME VARCHAR(255) UNIQUE NOT NULL,
                            COMPANY_ID INT NOT NULL,
                            CONSTRAINT FK_Company FOREIGN KEY (COMPANY_ID)
                                            REFERENCES Company(ID)
            )
            """;

    public CarDaoImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public void createTable() {
        dbClient.executeUpdate(createTableSql);
    }

    @Override
    public List<Car> getAll() {
        String sql = """
                SELECT * from CAR
                """;
        ResultSet rs = this.dbClient.executeQuery(sql);
        return convertRsToList(rs);
    }

    @Override
    public boolean insert(Car record) {
        String sql = """
                INSERT INTO CAR(name, company_id)
                VALUES (?, ?)
                """;
        try (PreparedStatement stmt = dbClient.getConnection().prepareStatement(sql)) {
            stmt.setString(1, record.name());
            stmt.setInt(2, record.companyId());
            return stmt.execute();
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Car> findByCompanyId(int id) {
        String sql = """
                
                select CAR.* from CAR
                            left join PUBLIC.CUSTOMER C on CAR.ID = C.RENTED_CAR_ID
                    where COMPANY_ID = ? and C.RENTED_CAR_ID IS NULL
                """;
        try (PreparedStatement stmt = dbClient.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return this.convertRsToList(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Car> convertRsToList(ResultSet rs) {
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<Car>(
                Long.MAX_VALUE, Spliterator.ORDERED) {
            @Override
            public boolean tryAdvance(Consumer<? super Car> action) {
                try {
                    if (!rs.next()) return false;
                    action.accept(createCarRecord(rs));
                    return true;
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }, false).toList();
    }

    private Car createCarRecord(ResultSet rs) throws SQLException {
        return new Car(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("COMPANY_ID"));
    }
}
