package carsharing.services;

import carsharing.entities.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();

    List<Car> getCarsByCompanyId(Integer companyId);

    void displayCarOptions(List<Car> cars);

    List<String> createCarListOptions(List<Car> cars);

    boolean addCar(Car car);
}
