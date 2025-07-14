package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.entities.Customer;
import carsharing.menuNavigator.MenuNavigator;
import carsharing.services.CustomerService;
import carsharing.services.CustomerServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class CustomerListMenu extends Menu {

    private final CustomerService customerService;
    private List<Customer> customers;

    public CustomerListMenu(DbClient dbClient, MenuNavigator navigator) {
        super(dbClient, navigator);

        this.customerService = new CustomerServiceImpl(dbClient);
        this.init();
    }

    private void init() {
        this.customers = this.customerService.getAllCustomers();
        this.loadOptions(this.customers);
    }

    private void loadOptions(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("The customer list is empty!");
            this.navigator.showLoginMenu();
        } else {
            System.out.println("Customer list:");
            final List<String> options = new ArrayList<>() {{
                for (int i = 0; i < customers.size(); i++) {
                    add(String.format("%s. %s", i + 1, customers.get(i).name()));
                }
            }};
            super.setOptions(options);
        }
    }

    @Override
    public void listenToCommand() {
        int input = this.readInt();

        if (input == 0) {
            this.navigator.showManagerMenu();
            return;
        }

        try {
            Customer customer = this.customers.get(input - 1);
            this.navigator.showCustomerMenu(customer);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("invalid selection, try again");
        }
    }
}
