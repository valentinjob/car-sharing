package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.entities.Customer;
import carsharing.menuNavigator.MenuNavigator;
import carsharing.services.CustomerService;
import carsharing.services.CustomerServiceImpl;

import java.util.List;

public class MainMenu extends Menu {

    private final List<String> options = List.of(
            "1. Log in as a manager",
            "2. Log in as a customer",
            "3. Create a customer",
            "0. Exit");

    private final CustomerService customerService;

    public MainMenu(DbClient client, MenuNavigator navigator) {
        super(client, navigator);
        this.customerService = new CustomerServiceImpl(client);
        super.setOptions(options);
    }

    @Override
    public void listenToCommand() {
        int input = this.readInt();

        switch (input) {
            case 0: {
                return;
            }
            case 1: {
                this.navigator.showManagerMenu();
                break;
            }
            case 2: {
                this.navigator.showCustomerListMenu();
                break;
            }
            case 3: {
                System.out.println("Enter the customer name:");
                String customerName = this.readString();

                if (!customerName.isEmpty()) {

                    this.customerService.addCustomer(new Customer(null, customerName, null));

                    System.out.println("The customer was added!\n");
                    this.navigator.showLoginMenu();
                } else {
                    System.out.println("customer name shouldn't be empty");
                    this.display();
                }
                break;
            }
            default: {
                System.out.println("invalid input");
            }
        }
    }
}
