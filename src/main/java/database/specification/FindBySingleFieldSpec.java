package database.specification;

public class FindBySingleFieldSpec<T> implements Specification {


    private Object[] params;
    private String tableName;
    private String columnName;

    public FindBySingleFieldSpec(T identifier, String tableName, String columnName){

        this.tableName = tableName;
        this.columnName = columnName;
        this.params = new Object[]{identifier};
    }

    @Override
    public QueryInfo toQueryInfo() {

        String sql = "select * from " + tableName + " where " + columnName +" =?";

        return new QueryInfo(sql,params);

    }


}
