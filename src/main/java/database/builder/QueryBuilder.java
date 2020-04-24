package database.builder;

import java.util.Map;

public class QueryBuilder {

    private StringBuilder builder;

    public QueryBuilder() {

    }

    private QueryBuilder(StringBuilder builder) {
        this.builder = builder;
    }

    public QueryBuilder insert(String tableName, Map<String, Object> params) {

        builder = new StringBuilder();
        StringBuilder helper = new StringBuilder();

        builder.append("INSERT INTO " + tableName + " (");
        helper.append(" VALUES(");

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.append(entry.getKey() + ",");
            helper.append(entry.getValue() + ",");
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        helper.deleteCharAt(helper.length() - 1);
        helper.append(")");

        builder.append(helper);

        return new QueryBuilder(builder);
    }

    public QueryBuilder update(String tableName, Map<String, Object> params) {

        builder = new StringBuilder();
        StringBuilder helper = new StringBuilder();

        builder.append("UPDATE " + tableName + " SET ");


        for (Map.Entry<String, Object> entry : params.entrySet()) {

            if (!entry.getKey().equals("id")) {
                builder.append(entry.getKey() + " = " + entry.getValue() + " ,");
            }
        }

        builder.deleteCharAt(builder.length() - 1);
        builder.append(" ");

        return new QueryBuilder(builder);
    }

    public QueryBuilder where() {
        return new QueryBuilder(builder.append("WHERE"));
    }

    public QueryBuilder field(String field, String value) {
        return new QueryBuilder(builder.append(" " + field + " = " + value));
    }

    public QueryBuilder delete(String tableName){
        return new QueryBuilder(builder.append("DELETE FROM " + tableName + " "));
    }

    public String getQuery() {
        return builder.toString();
    }

}
