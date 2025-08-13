package carsharing.services;

import carsharing.dao.CompanyDao;
import carsharing.dao.DAO;
import carsharing.dbClient.DbClient;
import carsharing.entities.Company;
import carsharing.utils.MenuUtils;

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
        return MenuUtils.createNumberedOptions(companies, Company::name);
    }

    @Override
    public void addCompany(Company company) {
        this.companyDao.insert(company);
    }

}
