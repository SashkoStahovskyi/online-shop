package com.stahovskyi.onlineshop.dao.jdbc;


import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;


public class JdbcConnectionFactory implements DataSource {
    private final String url;
    private final String username;
    private final String password;

    public JdbcConnectionFactory(Properties properties) {
        this.url = properties.getProperty("db.url");
        this.username = properties.getProperty("db.user");
        this.password = properties.getProperty("db.password");

        if (url == null) {
            throw new RuntimeException(" Uri not provided !");
        }
        if (username == null) {
            throw new RuntimeException(" Username not provided !");
        }
        if (password == null) {
            throw new RuntimeException(" Password not provided !");
        }
    }

    @SneakyThrows
    public Connection getConnection() {
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection(String username, String password) {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) {

    }

    @Override
    public void setLoginTimeout(int seconds) {

    }

    @Override
    public int getLoginTimeout() {
        return 0;
    }

    @Override
    public Logger getParentLogger() {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }

}


