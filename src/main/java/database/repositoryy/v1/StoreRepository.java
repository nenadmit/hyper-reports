package database.repositoryy.v1;

import database.repositoryy.Repository;
import database.specification.QueryInfo;
import database.specification.Specification;
import database.template.Template;
import deserialization.pojo.company.Store;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StoreRepository implements Repository<Store> {

    private Template<Store> template;

    public StoreRepository(Template<Store> template) {
        this.template = template;
    }

    @Override
    public Store create(Store store) {
        String sql = "insert into stores (name,address,fk_store_company) values (?,?,?)";

        int generatedKey = template.singleStatementExecution(sql, getObjectArray(store));

        if (generatedKey != 0) {
            store.setId(generatedKey);
        }
        return store;
    }

    @Override
    public List<Store> createAll(List<Store> list) {

        String sql = "insert into stores (name,address,fk_store_company) values (?,?,?)";

        List<Object[]> paramList = new ArrayList<>();

        list.forEach(store -> paramList.add(getObjectArray(store)));

        List<Integer> generatedKeys = template.batchExecution(sql, paramList);

        int counter = 0;
        for (Store store : list) {
            store.setId(generatedKeys.get(counter++));
        }

        return list;
    }

    @Override
    public Store update(Store store) {
        String sql = "update companies set name=?,address=?,fk_store_company=? where id=?";

        template.singleStatementExecution(sql, new Object[]{
                store.getName(),
                store.getAddress(),
                store.getCompany().getId()
        });
        return store;
    }

    @Override
    public void delete(Store store) {

        String sql = "delete from stores where id=?";

        template.singleStatementExecution(sql, new Object[]{store.getId()});

    }

    @Override
    public List<Store> query(Specification specification) {

        QueryInfo info = specification.toQueryInfo();

        return template.findAll(info.getSql(), info.getParams(), resultSet -> map(resultSet));

    }

    private Store map(ResultSet resultSet) throws SQLException {

        Store store = new Store(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("address"
                ));

        return store;
    }

    private Object[] getObjectArray(Store store) {
        return new Object[]{
                store.getName(),
                store.getAddress(),
                store.getCompanyId()
        };
    }

}
