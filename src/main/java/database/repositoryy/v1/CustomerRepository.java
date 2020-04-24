package database.repositoryy.v1;

import database.repositoryy.Repository;
import database.specification.QueryInfo;
import database.specification.Specification;
import database.template.Template;
import deserialization.pojo.company.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CustomerRepository implements Repository<Customer> {

    private Template<Customer> template;

    public CustomerRepository(Template<Customer> template) {
        this.template = template;
    }

    @Override
    public Customer create(Customer customer) {
        String sql = "insert into customers (uuid,name,address) values (?,?,?)";

        int generatedKey = template.singleStatementExecution(sql, getObjectArray(customer));

        if (generatedKey != 0) {
            customer.setId(generatedKey);
        }
        return customer;
    }

    @Override
    public List<Customer> createAll(List<Customer> list) {

        String sql = "insert into customers (uuid,name,address) values (?,?,?)";
        List<Object[]> paramList = new ArrayList<>();

        list.forEach(customer -> paramList.add(getObjectArray(customer)));

        List<Integer> generatedKeys = template.batchExecution(sql,paramList);

        int counter = 0;
        for(Customer customer:list){
            customer.setId(generatedKeys.get(counter++));
        }

        return list;
    }

    @Override
    public Customer update(Customer customer) {
        String sql = "update customers set uuid=?,name=?,address=? where id=?";

        template.singleStatementExecution(sql, new Object[]{
                customer.getUuid(),
                customer.getName(),
                customer.getAddress()
        });
        return customer;
    }

    @Override
    public void delete(Customer customer) {

        String sql = "delete from customers where id=?";

        template.singleStatementExecution(sql, new Object[]{customer.getId()});

    }

    @Override
    public List<Customer> query(Specification specification) {

        QueryInfo info = specification.toQueryInfo();

        return template.findAll(info.getSql(), info.getParams(), resultSet -> map(resultSet));

    }


    private Customer map(ResultSet resultSet) throws SQLException {

        return new Customer(
                resultSet.getInt("id"),
                resultSet.getLong("uuid"),
                resultSet.getString("name"),
                resultSet.getString("address")
        );
    }

    private Object[] getObjectArray(Customer customer){
        return new Object[]{
                customer.getUuid(),
                customer.getName(),
                customer.getAddress()
        };
    }

}
