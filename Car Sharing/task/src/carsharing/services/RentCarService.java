package carsharing.services;

import carsharing.entities.RentedCar;

public interface RentCarService {
    void rentCar(Integer customerId, Integer carId);

    RentedCar findRentedCar(Integer customerId);

    void returnRentedCar(Integer customerId);
}
