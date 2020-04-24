package database.specification;

import deserialization.pojo.company.Card;

public class FindAllSpecification implements Specification {

    private Object[] params;
    private String tableName;

    public FindAllSpecification(String tableName){

        this.tableName = tableName;

    }

    @Override
    public QueryInfo toQueryInfo() {

        String sql = "select * from " + tableName;

        return new QueryInfo(sql,params);

    }
}
