package database.service.interfaces;

import deserialization.pojo.company.Base;

import java.util.List;
import java.util.Optional;

public interface Service<T extends Base> {

    T save(T t);

    void delete(T t);

    T update(T t);

    List<T> createAll(List<T> list);

    Optional<T> findById(int id);
    List<T> findAll();

}
