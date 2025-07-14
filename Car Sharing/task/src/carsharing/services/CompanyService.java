package carsharing.services;

import carsharing.entities.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    void displayCompanyOptions(List<Company> companies);

    List<String> createCompanyOptionsList(List<Company> companies);

    void addCompany(Company company);
}
