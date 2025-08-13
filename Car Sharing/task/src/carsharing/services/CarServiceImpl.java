package carsharing.services;

import carsharing.dao.CarDao;
import carsharing.dao.CarDaoImpl;
import carsharing.dbClient.DbClient;
import carsharing.entities.Car;
import carsharing.utils.MenuUtils;

import java.util.List;

public class CarServiceImpl implements CarService {

    private final CarDao dao;

    public CarServiceImpl(DbClient dbClient) {
        this.dao = new CarDaoImpl(dbClient);
    }

    @Override
    public List<Car> getAllCars() {
        return List.of();
    }

    @Override
    public List<Car> getCarsByCompanyId(Integer companyId) {
        return this.dao.findByCompanyId(companyId);
    }

    @Override
    public void displayCarOptions(List<Car> cars) {
        if (cars.isEmpty()) {
            System.out.println("The car list is empty!");
            return;
        }
        System.out.println("Choose a car:");
        createCarListOptions(cars).forEach(System.out::println);
    }

    @Override
    public List<String> createCarListOptions(List<Car> cars) {
        return MenuUtils.createNumberedOptions(cars, Car::name);
    }

    @Override
    public boolean addCar(Car car) {
        return this.dao.insert(car);
    }
}
