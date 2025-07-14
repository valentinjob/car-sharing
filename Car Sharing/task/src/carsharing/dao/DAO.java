package carsharing.dao;

import java.util.List;

public interface DAO<T> {
    void createTable();

    List<T> getAll();

    boolean insert(T record);
}
