package com.example.niccolo_zanieri_swe.dao;

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
        psw = "Aut29SisOp30Fis230";

        try {
            pool = ConnectionPool.create(url, usr, psw);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test void CreateConnectionPoolTest() throws SQLException {
        pool = ConnectionPool.create(url, usr, psw);
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
}
