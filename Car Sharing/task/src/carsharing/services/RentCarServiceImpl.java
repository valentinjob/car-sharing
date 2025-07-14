package carsharing.services;

import carsharing.dao.RentCarDao;
import carsharing.dao.RentCarDaoImpl;
import carsharing.dbClient.DbClient;
import carsharing.entities.RentedCar;

public class RentCarServiceImpl implements RentCarService {


    private final RentCarDao rentCarDao;

    public RentCarServiceImpl(DbClient dbClient) {
        this.rentCarDao = new RentCarDaoImpl(dbClient);
    }

    @Override
    public void rentCar(Integer customerId, Integer carId) {
        this.rentCarDao.updateRentedCarById(customerId, carId);
    }

    @Override
    public RentedCar findRentedCar(Integer customerId) {
        return this.rentCarDao.findRentedCar(customerId);
    }

    @Override
    public void returnRentedCar(Integer customerId) {
        this.rentCarDao.deleteRentedCarById(customerId);
    }
}
