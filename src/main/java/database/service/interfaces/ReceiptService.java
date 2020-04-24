package database.service.interfaces;

import deserialization.pojo.company.Receipt;


import java.util.List;

public interface ReceiptService extends Service<Receipt> {


    List<Receipt> getAllByStore(int storeId);
    List<Receipt> getAllByCard(int cardId);
    List<Receipt> saveAll(List<Receipt> receipts);
}
