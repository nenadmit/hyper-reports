package database.service.interfaces;

import deserialization.pojo.company.Invoice;

import java.util.List;

public interface  InvoiceService extends Service<Invoice> {

    List<Invoice> getAllByStore(int storeId);
    List<Invoice> getAllByCard(int storeId);
    List<Invoice> getAllByCustomer(int customerId);
    List<Invoice> saveAll(List<Invoice> receipts);
}
