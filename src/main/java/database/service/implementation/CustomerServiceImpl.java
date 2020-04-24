package database.service.implementation;

import database.repositoryy.Repository;
import database.specification.FindBySingleFieldSpec;
import deserialization.pojo.company.Customer;
import database.service.interfaces.CustomerService;
import deserialization.pojo.company.Store;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl extends GenericService<Customer> implements CustomerService {

    private Repository<Customer> repository;

    public CustomerServiceImpl(Repository<Customer> repository) {
        super(repository, Customer.Fields.TABLE.getValue());
        this.repository = repository;
    }

    @Override
    public Optional<Customer> findByName(String name) {

        return checkIfExists(
                repository.query(new FindBySingleFieldSpec<String>(name,
                        Customer.Fields.TABLE.getValue(),
                        Customer.Fields.NAME.getValue()))
        );
    }

    @Override
    public Optional<Customer> findByUuid(long uuid) {
        return checkIfExists(
                repository.query(new FindBySingleFieldSpec<Long>(uuid,
                        Customer.Fields.TABLE.getValue(),
                        Customer.Fields.UUID.getValue()))
        );
    }

    @Override
    public Customer save(Customer customer) {

        Optional<Customer> optional = findByUuid(customer.getUuid());

        if (optional.isPresent()) {
            customer.setId(optional.get().getId());
            return customer;
        }
        return super.save(customer);
    }

    @Override
    public List<Customer> createAll(List<Customer> list) {

        for (Customer customer : list) {
            Optional<Customer> optional = findByName(customer.getName());

            if (optional.isPresent()) {
                customer.setId(optional.get().getId());
            } else {
                customer = save(customer);
            }
        }
        return list;
    }

    private Optional<Customer> checkIfExists(List<Customer> list) {

        if (list.isEmpty())
            return Optional.empty();

        return Optional.of(list.get(0));
    }
}
