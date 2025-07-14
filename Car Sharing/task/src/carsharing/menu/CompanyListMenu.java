package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.entities.Company;
import carsharing.menuNavigator.MenuNavigator;
import carsharing.services.CompanyService;
import carsharing.services.CompanyServiceImpl;

import java.util.List;

public class CompanyListMenu extends Menu {
    private final List<Company> companies;
    private final MenuNavigator navigator;

    private final CompanyService companyService;

    public CompanyListMenu(DbClient dbClient, MenuNavigator navigator) {
        super(dbClient, navigator);
        this.navigator = navigator;
        this.companyService = new CompanyServiceImpl(dbClient);
        this.companies = this.companyService.getAllCompanies();
        this.setOptions();
    }

    private void setOptions() {
        if (this.companies.isEmpty()) {
            System.out.println("The company list is empty!");
            this.navigator.showManagerMenu();
            return;
        }
        this.companyService.displayCompanyOptions(this.companies);
    }

    @Override
    public void listenToCommand() {
        int input = this.readInt();

        if (input == 0) {
            this.navigator.showManagerMenu();
            return;
        }

        try {
            Company company = this.companies.get(input - 1);

            this.navigator.showCarMenu(company);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("invalid selection, try again");
        }
    }
}
