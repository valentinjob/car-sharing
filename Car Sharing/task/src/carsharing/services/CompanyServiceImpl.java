package carsharing.services;

import carsharing.dao.CompanyDao;
import carsharing.dao.DAO;
import carsharing.dbClient.DbClient;
import carsharing.entities.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {
    private final DAO<Company> companyDao;

    public CompanyServiceImpl(DbClient dbClient) {
        this.companyDao = new CompanyDao(dbClient);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyDao.getAll();
    }

    @Override
    public void displayCompanyOptions(List<Company> companies) {
        System.out.println("Choose the company:");
        createCompanyOptionsList(companies).forEach(System.out::println);
    }

    @Override
    public List<String> createCompanyOptionsList(List<Company> companies) {
        if (companies.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> options = new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            options.add(String.format("%d. %s", i + 1, companies.get(i).name()));
        }
        options.add("0. Back");
        return options;
    }

    @Override
    public void addCompany(Company company) {
        this.companyDao.insert(company);
    }

}
