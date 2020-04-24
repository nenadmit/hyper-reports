import database.FlywayConfiguration;
import database.repositoryy.v2.RepositoryImpl;
import database.service.implementation.*;
import database.service.interfaces.*;
import deserialization.XmlParser;
import deserialization.pojo.company.*;
import jcommander.CommandLineParameters;
import org.flywaydb.core.Flyway;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplication")
public class Main {

    static final CommandLineParameters parameters = new CommandLineParameters();

    public static void main(String[] args) {

        Flyway flyway = FlywayConfiguration.getFlyway();
        flyway.migrate();

        File directory = new File("data/");
        List<File> files = new ArrayList<>();

        for (File file : directory.listFiles()) {
            files.add(file);
        }

        long start = System.nanoTime();

        files.stream().forEach(file -> getParser(file));

//        new SftpDownloader(new SftpDownloaderConfigurer(),new XmlFilesService()).downloadFiles("data/");

        long end = System.nanoTime();

        System.out.println("Runtime: " + (end - start));

//
//        Flyway flyway = FlywayConfiguration.getFlyway();
//        try{
//            flyway.validate();
//        }catch (Exception e){
//            flyway.migrate();
//        }
//
//        CommandLineParser parser = getParser();
//        parser.setParameters(parameters);
//        parser.parse(args);

    }

    private static List<Store> getParser(File file) {

        CompanyService companyService = new CompanyServiceImpl(new RepositoryImpl<Company>(Company.class));
        StoreService storeService = new StoreServiceImpl(new RepositoryImpl(Store.class));
        CustomerService customerService = new CustomerServiceImpl(new RepositoryImpl(Customer.class));
        CardService cardService = new CardServiceImpl(new RepositoryImpl(Card.class));
        ReceiptService receiptService = new ReceiptServiceImpl(new RepositoryImpl<>(Receipt.class));
        InvoiceService invoiceService = new InvoiceServiceImpl(new RepositoryImpl<>(Invoice.class));


        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Company company = null;
        try {
            company = new XmlParser<Company>(Company.class).parse(inputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        System.out.println("Parsing file " + file.getName());

        company = companyService.save(company);

        for (Store store : company.getStores()) {

            store.setCompanyId(company.getId());
            store = storeService.save(store);

            for (Receipt receipt : store.getReceipts()) {
                receipt.setStoreId(store.getId());

                if (receipt.getPaymentType().equals("card")) {
                    Card card = cardService.save(receipt.getCard());
                    receipt.setCardId(card.getId());
                }
            }
            receiptService.saveAll(store.getReceipts());

            for (Invoice invoice : store.getInvoices()) {

                if (invoice.getPaymentType().equals("card")) {
                    Card card = cardService.save(invoice.getCard());
                    invoice.setCardId(card.getId());
                }
                Customer customer = customerService.save(invoice.getCustomer());
                invoice.setCustomerId(customer.getId());
            }
            invoiceService.createAll(store.getInvoices());

        }


        return null;
    }




}
