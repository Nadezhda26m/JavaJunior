package org.example.hibern.repository;

import java.util.List;

public interface Repository<T, TId> {
    void insert(T tableClass);
    T readById(TId id);
    List<T> readAllData();
    void update(T tableClass);
    void delete(T tableClass);
    void deleteById(TId id);

}
