package database.service.implementation;

import database.repositoryy.Repository;
import database.service.interfaces.Service;
import database.specification.FindAllSpecification;
import database.specification.FindBySingleFieldSpec;
import deserialization.pojo.company.*;

import java.util.List;
import java.util.Optional;

public class GenericService<T extends Base> implements Service<T> {

    private Repository<T> repository;
    private String tableName;

    public GenericService(Repository<T> repository, String tableName) {
        this.repository = repository;
        this.tableName = tableName;
    }

    @Override
    public T save(T t) {
        return repository.create(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public T update(T t) {
        return repository.update(t);
    }

    @Override
    public List<T> createAll(List<T> list) {
        return repository.createAll(list);
    }

    @Override
    public Optional<T> findById(int id) {
        List<T> list = repository.query(new FindBySingleFieldSpec(id, tableName,"id"));

        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    @Override
    public List<T> findAll() {
        return repository.query(new FindAllSpecification(tableName));
    }


}
