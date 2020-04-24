package database.service.implementation;

import database.repositoryy.Repository;
import database.service.interfaces.InvoiceService;
import database.specification.FindBySingleFieldSpec;
import deserialization.pojo.company.Invoice;

import java.util.List;

public class InvoiceServiceImpl extends GenericService<Invoice> implements InvoiceService {

    private Repository<Invoice> repository;

    public InvoiceServiceImpl(Repository<Invoice> repository) {
        super(repository, Invoice.Fields.TABLE.getValue());
        this.repository = repository;
    }

    @Override
    public List<Invoice> getAllByStore(int storeId) {
        return repository.query(new FindBySingleFieldSpec<>(storeId,
                Invoice.Fields.TABLE.getValue(),
                Invoice.Fields.FK_INVOICE_STORE.getValue()));
    }

    @Override
    public List<Invoice> getAllByCard(int cardId) {
        return repository.query(new FindBySingleFieldSpec<>(cardId,
                Invoice.Fields.TABLE.getValue(),
                Invoice.Fields.FK_INVOICE_CARD.getValue()));
    }

    @Override
    public List<Invoice> getAllByCustomer(int customerId) {
        return repository.query(new FindBySingleFieldSpec<>(customerId,
                Invoice.Fields.TABLE.getValue(),
                Invoice.Fields.FK_INVOICE_CUSTOMER.getValue()));
    }

    @Override
    public List<Invoice> saveAll(List<Invoice> invoices) {
        return repository.createAll(invoices);
    }
}
