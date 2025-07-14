package carsharing.menuNavigator;

import carsharing.entities.Company;
import carsharing.entities.Customer;

public interface MenuNavigator {
    void startMenu();

    void showLoginMenu();

    void showManagerMenu();

    void showCompanyListMenu();

    void showCarMenu(Company selectedCompany);

    void showCarListMenu(Company selectedCompany);

    void showCustomerListMenu();

    void showCustomerMenu(Customer selectedCustomer);

    void showMyRentedCarMenu(Customer selectedCustomer);

    void showRentCarMenu(Customer customer);
}
