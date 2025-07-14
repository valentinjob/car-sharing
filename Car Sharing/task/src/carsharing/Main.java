package carsharing;

import carsharing.dao.*;
import carsharing.dbClient.DbClient;
import carsharing.dbClient.H2DbClient;
import carsharing.entities.Car;
import carsharing.entities.Company;
import carsharing.menuNavigator.MenuNavigator;
import carsharing.menuNavigator.MenuNavigatorImpl;

public class Main {

    public static void main(String[] args) {
        String dbName = args.length > 1 ? args[1] : "";

        try (DbClient dbClient = new H2DbClient(dbName)) {
            DAO<Company> companyDao = new CompanyDao(dbClient);
            DAO<Car> carDao = new CarDaoImpl(dbClient);
            CustomerDao customerDao = new CustomerDaoImpl(dbClient);

            companyDao.createTable();
            carDao.createTable();
            customerDao.createTable();

            MenuNavigator navigator = new MenuNavigatorImpl(dbClient);
            navigator.startMenu();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}