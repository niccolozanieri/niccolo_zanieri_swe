package com.example.niccolo_zanieri_swe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    public ConnectionPool(String url, String usr, String psw, List<Connection> pool) {
        this.url = url;
        this.password = psw;
        this.user = usr;
        this.connectionPool = pool;
    }

    public static ConnectionPool create(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(ConnectionPool.createConnection(url, user, password));
        }
        return new ConnectionPool(url, user, password, pool);
    }

    public Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

    public int getAvailableConnectionsNumber() {
        return connectionPool.size();
    }

    public int getOpenConnectionsNumber() throws SQLException {
        int count = 0;
        for(Connection c : connectionPool) {
            if(c.isValid(0)) {
                count++;
            }
        }

        return count;
    }

    private String url;
    private String user;
    private String password;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;
}
