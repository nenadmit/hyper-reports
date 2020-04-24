package database.repositoryy.v1;
import database.repositoryy.Repository;
import database.specification.QueryInfo;
import database.specification.Specification;
import database.template.Template;
import deserialization.pojo.company.Receipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ReceiptRepository implements Repository<Receipt> {

    private Template<Receipt> template;

    public ReceiptRepository(Template<Receipt> template){
        this.template = template;
    }

    @Override
    public Receipt create(Receipt receipt) {
        String sql = "insert into receipts (total,date_time,payment_type,fk_receipt_store,fk_receipt_card) " +
                "values (?,?,?,?,?)";

        int generatedKey = template.singleStatementExecution(sql, getObjectArray(receipt));

        if(generatedKey != 0){
            receipt.setId(generatedKey);
        }
        return receipt;
    }


    @Override
    public List<Receipt> createAll(List<Receipt> list) {

        String sql = "insert into receipts (total,date_time,payment_type,fk_receipt_store,fk_receipt_card) " +
                "values (?,?,?,?,?)";

        List<Object[]> paramList = new ArrayList<>();

        list.forEach(receipt -> paramList.add(getObjectArray(receipt)));

        List<Integer> generatedKeys = template.batchExecution(sql,paramList);

        int counter = 0;
        for(Receipt receipt:list){
           receipt.setId(generatedKeys.get(counter++));
        }

        return list;
    }

    @Override
    public Receipt update(Receipt receipt) {

        String sql = "update receipts set total=?,date_time=?,payment_type=?,fk_receipt_store=?, fk_receipt_card=? where id=?";
        Object isPaymentByCard = receipt.getPaymentType().equals("card") ? receipt.getCard().getId() : null;

        template.singleStatementExecution(sql,new Object[]{
                receipt.getTotal(),
                receipt.getDatetime(),
                receipt.getPaymentType(),
                receipt.getStore().getId(),
                isPaymentByCard,
                receipt.getId()
        });
        return receipt;
    }

    @Override
    public void delete(Receipt receipt) {

        String sql = "delete from receipts where id=?";

        template.singleStatementExecution(sql,new Object[]{receipt.getId()});

    }

    @Override
    public List<Receipt> query(Specification specification) {

        QueryInfo info = specification.toQueryInfo();

        return template.findAll(info.getSql(),info.getParams(),resultSet -> map(resultSet));

    }

    private Receipt map(ResultSet resultSet) throws SQLException {

        return new Receipt(
                resultSet.getInt("id"),
                resultSet.getDouble("total"),
                resultSet.getTimestamp("date_time"),
                resultSet.getString("payment_type")
        );
    }

    private Object[] getObjectArray(Receipt receipt) {

        Object isPaymentByCard = receipt.getPaymentType().equals("card") ? receipt.getCardId() : null;

        return new Object[]{
                receipt.getTotal(),
                receipt.getDatetime(),
                receipt.getPaymentType(),
                receipt.getStoreId(),
                isPaymentByCard
        };


    }
}
