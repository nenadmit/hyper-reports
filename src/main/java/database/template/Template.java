package database.template;

import java.util.List;
import java.util.Optional;

public interface Template<T> {


    List<T> findAll(String sql, RowMapper<T> rowMapper);
    List<T> findAll(String sql,Object[] params ,RowMapper<T> rowMapper);

    Optional<T> findOne (String sql, Object[] params, RowMapper<T> rowMapper);

    int singleStatementExecution(String sql, Object[] params);
    List<Integer> batchExecution(String sql, List<Object[]> params);
}
