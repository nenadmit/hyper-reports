package database.service.interfaces;

import deserialization.pojo.company.Store;

import java.util.List;
import java.util.Optional;

public interface StoreService extends Service<Store> {

    List<Store> findByCompany(int companyId);
    Optional<Store> findByName(String name);
    List<Store> getByCompanyRankedByTurnover(int companyId, int month, int year);
}
