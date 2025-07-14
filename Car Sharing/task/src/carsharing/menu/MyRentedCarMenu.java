package carsharing.menu;

import carsharing.dao.RentCarDao;
import carsharing.dao.RentCarDaoImpl;
import carsharing.dbClient.DbClient;
import carsharing.entities.Customer;
import carsharing.entities.RentedCar;
import carsharing.menuNavigator.MenuNavigator;

public class MyRentedCarMenu extends Menu {

    private final Customer selectedCustomer;

    private final RentedCar car;

    public MyRentedCarMenu(DbClient dbClient, MenuNavigator navigator, Customer selectedCustomer) {
        super(dbClient, navigator);
        this.selectedCustomer = selectedCustomer;

        this.car = this.findRentedCar();
    }

    private void printRentedCarInfo(RentedCar car) {
        System.out.println("Your rented car: ");
        System.out.println(car.carName());
        System.out.println("Company:");
        System.out.println(car.companyName());
        System.out.println();
    }

    private RentedCar findRentedCar() {
        RentCarDao dao = new RentCarDaoImpl(this.dbClient);
        RentedCar rentedCar = dao.findRentedCar(this.selectedCustomer.id());
        if (rentedCar == null) {
            System.out.println("You didn't rent a car!");
            this.navigator.showCustomerMenu(this.selectedCustomer);
        }

        return rentedCar;
    }

    @Override
    public void listenToCommand() {
        this.printRentedCarInfo(this.car);
        this.navigator.showCustomerMenu(this.selectedCustomer);
    }
}
