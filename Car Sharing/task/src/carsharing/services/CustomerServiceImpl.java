package carsharing.services;

import carsharing.dao.CustomerDao;
import carsharing.dao.CustomerDaoImpl;
import carsharing.dbClient.DbClient;
import carsharing.entities.Customer;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;


    public CustomerServiceImpl(DbClient dbClient) {
        this.customerDao = new CustomerDaoImpl(dbClient);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerDao.getAll();
    }

    @Override
    public void addCustomer(Customer customer) {
        this.customerDao.insert(customer);
    }

}
