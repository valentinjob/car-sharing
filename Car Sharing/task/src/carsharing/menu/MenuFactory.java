package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.entities.Company;
import carsharing.entities.Customer;
import carsharing.menuNavigator.MenuNavigator;

public class MenuFactory {
    private final DbClient dbClient;
    private final MenuNavigator navigator;

    public MenuFactory(DbClient dbClient, MenuNavigator menuNavigator) {
        this.dbClient = dbClient;
        this.navigator = menuNavigator;
    }

    public Menu createLoginMenu() {
        return new MainMenu(dbClient, navigator);
    }

    public Menu createManagerMenu() {
        return new ManagerMenu(dbClient, navigator);
    }

    public Menu createCompanyListMenu() {
        return new CompanyListMenu(dbClient, navigator);
    }

    public Menu createCarMenu(Company selectedCompany) {
        return new CarMenu(dbClient, navigator, selectedCompany);
    }

    public Menu createCarListMenu(Company selectedCompany) {
        return new CarListMenu(dbClient, navigator, selectedCompany);
    }

    public Menu createCustomerListMenu() {
        return new CustomerListMenu(dbClient, navigator);
    }

    public Menu createCustomerMenu(Customer selectedCustomer) {
        return new CustomerMenu(dbClient, navigator, selectedCustomer);
    }

    public Menu createMyRentCarMenu(Customer selectedCustomer) {
        return new MyRentedCarMenu(dbClient, navigator, selectedCustomer);
    }

    public Menu createRentCarMenu(Customer customer) {
        return new RentCarMenu(dbClient, navigator, customer);
    }

}
