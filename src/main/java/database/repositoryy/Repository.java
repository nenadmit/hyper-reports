package database.repositoryy;

import database.specification.Specification;
import deserialization.pojo.company.Base;
import java.util.List;

public interface Repository<T extends Base> {

    T create(T t);
    List<T> createAll(List<T> list);

    T update(T t);

    void delete(T t);

    List<T> query(Specification specification);

}
