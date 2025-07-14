package carsharing.dao;

import carsharing.entities.RentedCar;

public interface RentCarDao {
    RentedCar findRentedCar(Integer customerId);

    void updateRentedCarById(Integer customerId, Integer carId);

    void deleteRentedCarById(Integer customerId);
}
