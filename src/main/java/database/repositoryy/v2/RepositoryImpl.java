package database.repositoryy.v2;

import database.builder.QueryBuilder;
import database.repositoryy.Repository;
import database.specification.QueryInfo;
import database.specification.Specification;
import database.template.TemplateImpl;
import deserialization.pojo.company.Base;
import deserialization.pojo.company.annotations.Column;
import deserialization.pojo.company.Company;
import deserialization.pojo.company.annotations.Table;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RepositoryImpl<T extends Base> implements Repository<T> {

    private Class<T> classPath;

    private final String tableName;

    public RepositoryImpl(Class<T> classPath) {
        this.classPath = classPath;
        this.tableName = classPath.getAnnotation(Table.class).name();
    }

    @Override
    public T create(T t) {

        Map<String, Object> params = getFieldsAsMap(classPath.getDeclaredFields());

        String sql = new QueryBuilder().insert(tableName, params).getQuery();

        int generatedKey = new TemplateImpl<Company>().singleStatementExecution(sql, getObjectArray(t));

        t.setId(generatedKey);

        return t;
    }

    @Override
    public List<T> createAll(List<T> list) {

        Map<String, Object> params = getFieldsAsMap(classPath.getDeclaredFields());
        String sql = new QueryBuilder().insert(tableName, params).getQuery();

        List<Object[]> paramsObject = new ArrayList<>();

        list.stream().forEach(param -> paramsObject.add(getObjectArray(param)));

        List<Integer> generatedKeys = new TemplateImpl<T>().batchExecution(sql, paramsObject);

        int counter = 0;
        for (T t : list) {
            t.setId(generatedKeys.get(counter++));
        }
        return list;
    }

    /**
     * Moving all the objects in an array one step to the left, so that the id goes last
     * and the rest of object go first.
     */
    @Override
    public T update(T t) {

        Map<String, Object> params = getFieldsAsMap(classPath.getDeclaredFields());
        String sql = new QueryBuilder().update(tableName, params).where().field("id", "?").getQuery();

        Object[] objectArray = getObjectArray(t);
        Object[] shiftToLeft = new Object[objectArray.length];

        shiftToLeft[shiftToLeft.length - 1] = objectArray[0];
        for (int x = 0; x < objectArray.length - 1; x++) {
            shiftToLeft[x] = objectArray[x + 1];
        }

        new TemplateImpl<>().singleStatementExecution(sql, shiftToLeft);

        return t;

    }

    @Override
    public void delete(T t) {

        String sql = new QueryBuilder().delete(tableName)
                .where()
                .field("id", "?").getQuery();

        new TemplateImpl<>().singleStatementExecution(sql, new Object[]{t.getId()});
    }

    @Override
    public List<T> query(Specification specification) {

        QueryInfo queryInfo = specification.toQueryInfo();

        return new TemplateImpl<T>().findAll(queryInfo.getSql(), queryInfo.getParams(), this::map);
    }

    private T map(ResultSet resultSet) throws SQLException {

        T t = null;

        try {
            t = classPath.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        for (Field field : classPath.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                try {
                    field.setAccessible(true);
                    if(field.getType() == Boolean.class){
                        field.set(t,resultSet.getBoolean(field.getAnnotation(Column.class).name()));
                    }else{
                        field.set(t, resultSet.getObject(field.getAnnotation(Column.class).name()));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            t.setId(resultSet.getInt("id"));
        }
        return t;
    }

    private Object[] getObjectArray(T t) {

        List<Object> objects = new ArrayList<>();

        for (Field filed : classPath.getDeclaredFields()) {

            if (filed.isAnnotationPresent(Column.class)) {

                try {
                    filed.setAccessible(true);
                    objects.add(filed.get(t));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        Object[] params = new Object[objects.size()];

        int i = 0;
        for (Object o : objects) {
            params[i] = objects.get(i);
            i++;
        }
        return params;
    }

    public Map<String, Object> getFieldsAsMap(Field[] fields) {

        Map<String, Object> params = new LinkedHashMap<>();

        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> params.put(field.getAnnotation(Column.class).name(), "?"));

        return params;
    }
}
