package sftp;

import database.DbConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class XmlFilesService {


    public void saveAll(List<String> filenames) {

        String sql = "insert into parsed_xml_files values (?)";
        Connection connection = DbConnectionPool.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for(String filename:filenames){
                preparedStatement.setString(1,filename);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Optional<String> find(String filename) {

        String sql = "select * from parsed_xml_files where filename=?";

        Connection connection = DbConnectionPool.getConnection();
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, filename);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(filename);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                connection.close();
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
//
//    public List<String> findAll(){
//
//        String sql = "select * from "
//
//    }

}
