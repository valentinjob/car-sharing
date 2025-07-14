package carsharing.menuNavigator;

import carsharing.dbClient.DbClient;
import carsharing.entities.Company;
import carsharing.entities.Customer;
import carsharing.menu.Menu;
import carsharing.menu.MenuFactory;

public class MenuNavigatorImpl implements MenuNavigator {

    private final MenuFactory menuFactory;

    private Menu activeMenu;

    public MenuNavigatorImpl(DbClient dbClient) {
        this.menuFactory = new MenuFactory(dbClient, this);
    }


    @Override
    public void startMenu() {
        this.changeMenu(this.menuFactory.createLoginMenu());
    }

    @Override
    public void showLoginMenu() {
        this.changeMenu(menuFactory.createLoginMenu());
    }

    @Override
    public void showCompanyListMenu() {
        this.changeMenu(menuFactory.createCompanyListMenu());
    }

    @Override
    public void showCarMenu(Company selectedCompany) {
        this.changeMenu(menuFactory.createCarMenu(selectedCompany));
    }

    @Override
    public void showCarListMenu(Company selectedCompany) {
        this.changeMenu(menuFactory.createCarListMenu(selectedCompany));
    }

    @Override
    public void showCustomerListMenu() {
        this.changeMenu(menuFactory.createCustomerListMenu());
    }

    @Override
    public void showManagerMenu() {
        this.changeMenu(menuFactory.createManagerMenu());
    }

    @Override
    public void showCustomerMenu(Customer selectedCustomer) {
        this.changeMenu(menuFactory.createCustomerMenu(selectedCustomer));
    }

    @Override
    public void showMyRentedCarMenu(Customer selectedCustomer) {
        this.changeMenu(menuFactory.createMyRentCarMenu(selectedCustomer));
    }

    @Override
    public void showRentCarMenu(Customer customer) {
        this.changeMenu(menuFactory.createRentCarMenu(customer));
    }

    private void changeMenu(Menu menu) {
        this.activeMenu = menu;
        this.renderMenu();
    }

    private void renderMenu() {
        this.activeMenu.display();
    }
}
