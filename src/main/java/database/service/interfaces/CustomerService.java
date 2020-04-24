package database.service.interfaces;

import deserialization.pojo.company.Customer;

import java.util.Optional;

public interface  CustomerService extends Service<Customer> {

    Optional<Customer> findByName(String name);
    Optional<Customer> findByUuid(long uuid);
}
