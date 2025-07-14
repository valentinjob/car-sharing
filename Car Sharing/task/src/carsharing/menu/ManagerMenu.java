package carsharing.menu;

import carsharing.dbClient.DbClient;
import carsharing.entities.Company;
import carsharing.menuNavigator.MenuNavigator;
import carsharing.services.CompanyService;
import carsharing.services.CompanyServiceImpl;

import java.util.List;

public class ManagerMenu extends Menu {
    private final List<String> options = List.of("1. Company list", "2. Create a company", "0. Back");

    private final CompanyService companyService;

    public ManagerMenu(DbClient dbClient, MenuNavigator navigator) {
        super(dbClient, navigator);
        this.companyService = new CompanyServiceImpl(dbClient);
        super.setOptions(options);
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
                navigator.showCompanyListMenu();
                break;
            }
            case 2: {
                System.out.println("Enter the company name:");
                String companyName = this.readString();

                if (!companyName.isEmpty()) {
                    companyService.addCompany(new Company(null, companyName));

                    System.out.println("The company was created! \n");

                    this.navigator.showManagerMenu();
                } else {
                    System.out.println("company name shouldn't be empty");
                    this.display();
                }
                break;
            }
        }
    }
}
