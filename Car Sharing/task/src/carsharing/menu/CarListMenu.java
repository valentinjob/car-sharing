package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.entities.Company;
import carsharing.menuNavigator.MenuNavigator;
import carsharing.services.CarService;
import carsharing.services.CarServiceImpl;

public class CarListMenu extends Menu {
    private final Company selectedCompany;

    private final CarService carService;

    public CarListMenu(DbClient dbClient, MenuNavigator navigator, Company company) {
        super(dbClient, navigator);
        this.selectedCompany = company;
        this.carService = new CarServiceImpl(dbClient);

        this.init();
    }

    private void init() {
        var cars = this.carService.getCarsByCompanyId(this.selectedCompany.id());

        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            return;
        }

        var options = this.carService.createCarListOptions(cars);

        if (!options.isEmpty()) {
            // remove a back option
            options.removeLast();
        }
        super.setOptions(options);

    }

    @Override
    public void listenToCommand() {
        this.navigator.showCarMenu(this.selectedCompany);
    }
}
