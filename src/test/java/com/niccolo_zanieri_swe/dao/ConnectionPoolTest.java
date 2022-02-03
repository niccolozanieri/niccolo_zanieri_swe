package com.niccolo_zanieri_swe.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest {
    private String url;
    private String usr;
    private String psw;
    private ConnectionPool pool;

    @BeforeEach void setUpPool() {
        url = "jdbc:postgresql://localhost:5432/swe_exam_db";
        usr = "niccolo_zanieri";
        psw = "basidati2022";

        try {
            pool = ConnectionPool.getConnectionPool(url, usr, psw);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test void CreateConnectionPoolTest() throws SQLException {
        pool = ConnectionPool.getConnectionPool(url, usr, psw);
        Assertions.assertEquals(pool.getOpenConnectionsNumber(), 10);
        Assertions.assertEquals(10, pool.getSize());
    }

    @Test void GetConnectionTest() {
        try {
            Assertions.assertTrue(pool.getConnection().isValid(0));
            Assertions.assertEquals(10, pool.getSize());
            Assertions.assertEquals(9, pool.getAvailableConnectionsNumber());
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test void ReleaseConnectionTest() {
        Connection test_conn = pool.getConnection();
        pool.releaseConnection(test_conn);
        Assertions.assertEquals(10, pool.getAvailableConnectionsNumber());
    }

    @Test void ReleaseEveryConnection() {
        Connection c1 = pool.getConnection();
        Connection c2 = pool.getConnection();
        Connection c3 = pool.getConnection();

        pool.releaseEveryConnection();
        Assertions.assertEquals(10, pool.getAvailableConnectionsNumber());
    }
}
