package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.entities.Car;
import carsharing.entities.Company;
import carsharing.entities.Customer;
import carsharing.entities.RentedCar;
import carsharing.menuNavigator.MenuNavigator;
import carsharing.services.*;

import java.util.List;

public class RentCarMenu extends Menu {

    private final MenuNavigator navigator;

    private final CompanyService companyService;

    private final CarService carService;

    private final RentCarService rentCarService;

    private final Customer customer;

    private final List<Company> companies;

    public RentCarMenu(DbClient dbClient, MenuNavigator navigator, Customer customer) {
        super(dbClient, navigator);
        this.navigator = navigator;
        this.companyService = new CompanyServiceImpl(dbClient);
        this.carService = new CarServiceImpl(dbClient);
        this.rentCarService = new RentCarServiceImpl(dbClient);
        this.companies = this.companyService.getAllCompanies();
        this.customer = customer;

        this.init();
    }


    private void init() {
        RentedCar car = this.rentCarService.findRentedCar(this.customer.id());
        if (car != null) {
            System.out.println("You've already rented a car! \n");
            this.navigator.showCustomerMenu(this.customer);
            return;
        }
        this.loadOptions(this.companies);
    }


    private void loadOptions(List<Company> companies) {
        var options = this.companyService.createCompanyOptionsList(companies);
        if (options != null) {
            super.setOptions(options);
        }
    }


    @Override
    public void listenToCommand() {
        int input = this.readInt();

        if (input == 0) {
            this.navigator.showCustomerMenu(this.customer);
            return;
        }

        Company selectedCompany = this.companies.get(input - 1);

        Car selectedCar = this.listenToCarSelection(selectedCompany);

        if (selectedCar != null) {
            this.rentCarService.rentCar(this.customer.id(), selectedCar.id());

            System.out.printf("You rented '%s'\n", selectedCar.name());

        }
        this.navigator.showCustomerMenu(this.customer);
    }

    private Car listenToCarSelection(Company company) {

        List<Car> cars = carService.getCarsByCompanyId(company.id());
        carService.displayCarOptions(cars);

        int option = this.readInt();

        if (option == 0) {
            this.navigator.showCustomerMenu(this.customer);
            return null;
        }
        return cars.get(option - 1);
    }
}
