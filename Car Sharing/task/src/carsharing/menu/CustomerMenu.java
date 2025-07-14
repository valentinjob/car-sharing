package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.entities.Customer;
import carsharing.entities.RentedCar;
import carsharing.menuNavigator.MenuNavigator;
import carsharing.services.RentCarService;
import carsharing.services.RentCarServiceImpl;

import java.util.List;


public class CustomerMenu extends Menu {

    private final Customer selectedCustomer;

    private final RentCarService rentCarService;

    private final List<String> options = List.of("1. Rent a car", "2. Return a rented car", "3. My rented car", "0. Back");

    public CustomerMenu(DbClient dbClient, MenuNavigator navigator, Customer selectedCustomer) {
        super(dbClient, navigator);
        this.setOptions(options);
        this.selectedCustomer = selectedCustomer;
        this.rentCarService = new RentCarServiceImpl(dbClient);
    }

    @Override
    public void listenToCommand() {
        int input = this.readInt();

        switch (input) {
            case 0: {
                this.navigator.showLoginMenu();
                break;
            }
            case 1: {
                this.navigator.showRentCarMenu(this.selectedCustomer);
                break;
            }
            case 2: {

                RentedCar car = this.rentCarService.findRentedCar(this.selectedCustomer.id());
                if (car == null) {
                    System.out.println("You didn't rent a car!");
                    this.navigator.showCustomerMenu(this.selectedCustomer);
                    return;
                }

                this.rentCarService.returnRentedCar(this.selectedCustomer.id());
                System.out.println("You've returned a rented car!\n");
                this.display();
                break;
            }
            case 3: {
                this.navigator.showMyRentedCarMenu(this.selectedCustomer);
                break;
            }
        }
    }
}
