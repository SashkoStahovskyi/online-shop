package com.stahovskyi.onlineshop.dao.jdbc;


import lombok.SneakyThrows;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Logger;


public class ConnectionFactory implements DataSource {
    private final String url;
    private final String username;
    private final String password;

    public ConnectionFactory(Properties properties) {
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

  /*  private final CashedConnection connection;

    @SneakyThrows
    public ConnectionFactory(Properties properties) { // todo --> need other exception??

        this.uri = properties.getProperty("URI");
        this.username = properties.getProperty("USERNAME");
        this.password = properties.getProperty("PASSWORD");



        Connection realConnection = DriverManager.getConnection(uri, username, password);
        this.connection = new CashedConnection(realConnection);
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        CashedConnection connection = this.connection;
        while (connection.isBusy);
        connection.isBusy = true;
        return connection;
    } */

