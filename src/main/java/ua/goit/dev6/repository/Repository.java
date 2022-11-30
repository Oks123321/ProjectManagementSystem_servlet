package ua.goit.dev6.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T>{
    T save(T entity);
    void delete(T entity);
    T update(T entity);
    Optional<T> findById(Long id);
    List<T> findAll();
    List<T> findByListOfID(List<Long> idList);

}
