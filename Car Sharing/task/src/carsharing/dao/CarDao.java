package carsharing.dao;

import carsharing.entities.Car;

import java.util.List;

public interface CarDao extends DAO<Car> {
    List<Car> findByCompanyId(int id);
}
