package ru.javawebinar.topjava.crud;

import java.util.List;

/**
 * Created by Ivan on 28.03.2017.
 */
public interface CRUD_Interface<T> {
    void save(T obj);
    T read(int id);
    void update(int id, T obj);
    void delete(int id);
    List<T> findAll();
}
