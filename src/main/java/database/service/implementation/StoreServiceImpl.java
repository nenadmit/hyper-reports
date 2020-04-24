package database.service.implementation;

import database.repositoryy.Repository;
import database.service.interfaces.StoreService;
import database.specification.FindBySingleFieldSpec;
import database.specification.StoresByCompanyOrderedByTurnoverSpec;
import deserialization.pojo.company.Company;
import deserialization.pojo.company.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StoreServiceImpl extends GenericService<Store> implements StoreService {

    private Repository<Store> repository;

    public StoreServiceImpl(Repository<Store> repository) {
        super(repository, Company.Fields.TABLE.getValue());
        this.repository = repository;
    }

    @Override
    public List<Store> findByCompany(int companyId) {
        return repository.query(
                new FindBySingleFieldSpec<>(companyId,
                        Store.Fields.TABLE.getValue(),
                        Store.Fields.FK_STORE_COMPANY.getValue()));
    }

    @Override
    public Optional<Store> findByName(String name) {

        List<Store> list =  repository.query(new FindBySingleFieldSpec<>(name,
                Store.Fields.TABLE.getValue(),
                Store.Fields.NAME.getValue()));

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<Store> getByCompanyRankedByTurnover(int companyId, int month, int year) {
        return repository.query(new StoresByCompanyOrderedByTurnoverSpec(companyId,month,year));
    }

    @Override
    public List<Store> createAll(List<Store> list){

        for(Store store:list) {
            Optional<Store> optional = findByName(store.getName());

            if (optional.isPresent()) {
                store.setId(optional.get().getId());
            } else {
                store = save(store);
            }
        }
        return list;

    }

    @Override
    public Store save(Store store){

       Optional<Store> optional = findByName(store.getName());

        if(optional.isPresent()){
            store.setId(optional.get().getId());
            return store;
        }
        return super.save(store);
    }
}
