package ru.itis.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class HikariSimpleDataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(HikariDataSource.class.getResourceAsStream("/database.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.setUsername(properties.getProperty("db.username"));
        config.setPassword(properties.getProperty("db.password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public HikariSimpleDataSource() {
    }

    public static DataSource getDataSource(){
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl( properties.getProperty("db.url") );
        hikariConfig.setUsername( properties.getProperty("db.username") );
        hikariConfig.setPassword( properties.getProperty("db.password") );
        hikariConfig.addDataSourceProperty( "cachePrepStmts" , "true" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        hikariConfig.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        return new HikariDataSource(hikariConfig);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
