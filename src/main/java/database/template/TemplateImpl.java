package database.template;

import database.DbConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TemplateImpl<T> implements Template<T> {

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    @Override
    public List<T> findAll(String sql, RowMapper<T> rowMapper) {

        List<T> list = new ArrayList<>();

        connection = DbConnectionPool.getConnection();

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return list;
    }

    @Override
    public List<T> findAll(String sql, Object[] params, RowMapper<T> rowMapper) {

        List<T> list = new ArrayList<>();
        try {
            connection = DbConnectionPool.getConnection();
            statement = connection.prepareStatement(sql);

            int counter = 1;
            for (Object object : params) {
                statement.setObject(counter, object);
                counter++;
            }
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                list.add(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
        return list;
    }

    @Override
    public Optional<T> findOne(String sql, Object[] params, RowMapper<T> rowMapper) {

        connection = DbConnectionPool.getConnection();

        try {
            statement = connection.prepareStatement(sql);
            int counter = 1;
            for (Object param : params) {
                statement.setObject(counter, param);
                counter++;
            }

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
           closeConnection();
        }

        return Optional.empty();
    }

    @Override
    public int singleStatementExecution(String sql, Object[] params) {

        connection = DbConnectionPool.getConnection();

        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            for (Object param : params) {
                statement.setObject(counter, param);
                counter++;
            }

            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return 0;
    }


    @Override
    public List<Integer> batchExecution(String sql, List<Object[]> params) {

        connection = DbConnectionPool.getConnection();

        List<Integer> generatedKeys = new ArrayList<>();

        try {

            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            for (Object[] objects : params) {

                int counter = 1;
                for (Object object : objects) {

                    statement.setObject(counter, object);
                    counter++;
                }
                statement.addBatch();
            }
            statement.executeBatch();
            resultSet = statement.getGeneratedKeys();

            while (resultSet.next()) {
                generatedKeys.add(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return generatedKeys;


    }

    private void closeConnection() {

        try {
            statement.close();
            if(!resultSet.isClosed()){
                resultSet.close();
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
