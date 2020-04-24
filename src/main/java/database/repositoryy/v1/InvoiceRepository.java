package database.repositoryy.v1;
import database.repositoryy.Repository;
import database.specification.QueryInfo;
import database.specification.Specification;
import database.template.Template;
import deserialization.pojo.company.Invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class InvoiceRepository implements Repository<Invoice> {

    private Template<Invoice> template;

    public InvoiceRepository(Template<Invoice> template){
        this.template = template;
    }

    @Override
    public Invoice create(Invoice invoice) {
        String sql = "insert into invoices (total,date_time,payment_type,fk_invoice_store,fk_invoice_card, fk_invoice_customer) " +
                "values (?,?,?,?,?)";

        int generatedKey = template.singleStatementExecution(sql, getObjectArray(invoice));

        if(generatedKey != 0){
            invoice.setId(generatedKey);
        }
        return invoice;
    }

    @Override
    public List<Invoice> createAll(List<Invoice> list) {

        String sql = "insert into invoices (total,date_time,payment_type,fk_invoice_store,fk_invoice_card, fk_invoice_customer) " +
                "values (?,?,?,?,?,?)";

        List<Object[]> paramList = new ArrayList<>();

        list.forEach(invoice -> paramList.add(getObjectArray(invoice)));

        List<Integer> generatedKeys = template.batchExecution(sql,paramList);

        int counter = 0;
        for(Invoice invoice:list){
            invoice.setId(generatedKeys.get(counter++));
        }

        return list;
    }

    @Override
    public Invoice update(Invoice invoice) {

        String sql = "update invoices set total=?,date_time=?,payment_type=?,fk_invoice_store=?, fk_invoice_card=?," +
                "fk_invoice_customer=? where id=?";
        Object isPaymentByCard = invoice.getPaymentType().equals("card")? invoice.getCard().getId():null;

        template.singleStatementExecution(sql,new Object[]{
                invoice.getTotal(),
                invoice.getDatetime(),
                invoice.getPaymentType(),
                invoice.getStore().getId(),
                isPaymentByCard,
                invoice.getCustomer().getId()
        });
        return invoice;
    }

    @Override
    public void delete(Invoice invoice) {

        String sql = "delete from invoices where id=?";

        template.singleStatementExecution(sql,new Object[]{invoice.getId()});

    }

    @Override
    public List<Invoice> query(Specification specification) {

        QueryInfo info = specification.toQueryInfo();

        return template.findAll(info.getSql(),info.getParams(),resultSet -> map(resultSet));

    }

    private Invoice map(ResultSet resultSet) throws SQLException {

        return new Invoice(
                resultSet.getInt("id"),
                resultSet.getDouble("total"),
                resultSet.getTimestamp("date_time"),
                resultSet.getString("payment_type")
        );
    }

    private Object[] getObjectArray(Invoice invoice) {

        Object isPaymentByCard = invoice.getPaymentType().equals("card")? invoice.getCardId():null;

        return new Object[]{
                invoice.getTotal(),
                invoice.getDatetime(),
                invoice.getPaymentType(),
                invoice.getStoreId(),
                isPaymentByCard,
                invoice.getCustomerId()

        };

    }
}
