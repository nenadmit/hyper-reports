package database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import configuration.AppConfigurer;
import configuration.DbConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnectionPool {

    private static DbConnectionPool dbConnectionPool;
    private HikariDataSource dataSource;
    private DbConfiguration dbConfig;


    private DbConnectionPool(){

        this.dbConfig = AppConfigurer.getConfiguration().getDbConfiguration();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbConfig.getUrl());
        config.setUsername(dbConfig.getUsername());
        config.setPassword(dbConfig.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);

    }

    public static Connection getConnection(){
        if(dbConnectionPool == null){
            dbConnectionPool = new DbConnectionPool();
        }

        try {
            return dbConnectionPool.dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }


}
