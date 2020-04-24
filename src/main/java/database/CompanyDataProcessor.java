package database;

import database.service.interfaces.*;
import deserialization.XmlParser;
import deserialization.pojo.company.Company;
import deserialization.pojo.company.Invoice;
import deserialization.pojo.company.Receipt;
import deserialization.pojo.company.Store;

import javax.xml.bind.JAXBException;
import java.io.File;

public class CompanyDataProcessor {

    private CompanyService companyService;
    private StoreService storeService;
    private CustomerService customerService;
    private CardService cardService;
    private ReceiptService receiptService;
    private InvoiceService invoiceService;
    private XmlParser<Company> parser;

    public CompanyDataProcessor(CompanyService companyService,
                                StoreService storeService,
                                CustomerService customerService,
                                CardService cardService,
                                ReceiptService receiptService,
                                InvoiceService invoiceService,
                                XmlParser parser) {
        this.companyService = companyService;
        this.storeService = storeService;
        this.customerService = customerService;
        this.cardService = cardService;
        this.receiptService = receiptService;
        this.invoiceService = invoiceService;
        this.parser = parser;
    }

    public void saveData(File file) {

        System.out.println("Processing file: " + file.getName());

        Company company = new Company();
        try {
            company = parser.parse(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        company = companyService.save(company);

        for (Store store : company.getStores()) {
            store.setCompany(company);
            store = storeService.save(store);

            for (Receipt receipt : store.getReceipts()) {

                receipt.setStore(store);
                if (receipt.getPaymentType().equals("card")) {
                    cardService.save(receipt.getCard());
                }
            }
            receiptService.saveAll(store.getReceipts());

            for (Invoice invoice : store.getInvoices()) {

                invoice.setStore(store);
                if (invoice.getPaymentType().equals("card")) {
                    cardService.save(invoice.getCard());
                }
                customerService.save(invoice.getCustomer());
            }
            invoiceService.saveAll(store.getInvoices());
        }

    }
}
