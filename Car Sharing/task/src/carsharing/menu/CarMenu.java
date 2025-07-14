package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.entities.Car;
import carsharing.entities.Company;
import carsharing.menuNavigator.MenuNavigator;
import carsharing.services.CarService;
import carsharing.services.CarServiceImpl;

import java.util.List;

public class CarMenu extends Menu {

    private final Company selectedCompany;

    private final CarService carService;

    private final List<String> options = List.of("1. Car list", "2. Create a car", "0. Back");

    public CarMenu(DbClient dbClient, MenuNavigator navigator, Company selectedCompany) {
        super(dbClient, navigator);
        this.setOptions(options);
        this.selectedCompany = selectedCompany;
        this.carService = new CarServiceImpl(dbClient);

        this.init();
    }

    private void init() {
        System.out.printf("'%s' company \n", this.selectedCompany.name());
    }

    @Override
    public void listenToCommand() {
        int input = this.readInt();

        switch (input) {
            case 0: {
                this.navigator.showManagerMenu();
                break;
            }
            case 1: {
                this.navigator.showCarListMenu(this.selectedCompany);
                break;
            }
            case 2: {
                System.out.println("Enter the car name:");
                String carName = this.readString();

                if (!carName.isEmpty()) {

                    Car newCar = new Car(null, carName, this.selectedCompany.id());
                    boolean result = this.carService.addCar(newCar);

                    if (result) {
                        System.out.println("The car was added!");
                        this.navigator.showCarMenu(this.selectedCompany);
                    } else {
                        System.out.println("The car was not added!");
                        this.display();
                    }

                } else {
                    System.out.println("car name shouldn't be empty");
                    this.display();
                }
                break;
            }
        }

    }
}
