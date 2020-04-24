package database.service.interfaces;

import deserialization.pojo.company.Company;

import java.util.Optional;

public interface CompanyService extends Service<Company>{

    Optional<Company> findById(int id);
    Optional<Company> findByName(String name);
    Optional<Company> findByUuid(long uuid);

}
