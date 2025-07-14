package carsharing.dao;

import carsharing.dbClient.DbClient;
import carsharing.entities.RentedCar;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RentCarDaoImpl implements RentCarDao {

    private final DbClient dbClient;

    public RentCarDaoImpl(DbClient dbClient) {
        this.dbClient = dbClient;
    }

    @Override
    public RentedCar findRentedCar(Integer customerId) {
        final String query = """
                select CAR.NAME as carName, COMPANY.NAME as companyName
                from CUSTOMER
                inner join CAR on RENTED_CAR_ID = CAR.ID
                         inner join COMPANY on CAR.COMPANY_ID = COMPANY.ID
                where CUSTOMER.ID = ?
                """;
        try (PreparedStatement stmt = dbClient.getConnection().prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new RentedCar(
                        rs.getString("carName"),
                        rs.getString("companyName")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateRentedCarById(Integer customerId, Integer carId) {

        final String query = """
                update CUSTOMER 
                SET rented_car_id = ?
                where ID = ?
                """;
        try (PreparedStatement stmt = dbClient.getConnection().prepareStatement(query)) {
            stmt.setInt(1, carId);
            stmt.setInt(2, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRentedCarById(Integer customerId) {
        final String query = """
                update CUSTOMER
                set rented_car_id = NULL
                where ID = ?
                """;

        try (PreparedStatement stmt = dbClient.getConnection().prepareStatement(query)) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
