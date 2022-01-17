package com.niccolo_zanieri_swe.dao;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreatorDAOTest {
    private ConnectionPool pool;
    private CreatorDAO dao;

    @BeforeAll
    void setPool() {
        String url = "jdbc:postgresql://localhost:5432/swe_exam_db";
        String usr = "niccolo_zanieri";
        String psw = "basidati2022";

        try {
            pool = ConnectionPool.create(url, usr, psw);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    void setUp() {
        dao = new CreatorDAO(pool);
    }

    @Test
    void insertCreatorTest() {
        Connection c = null;
        Statement stmt = null;

        try {
            c = pool.getConnection();
            stmt = c.createStatement();

            try {
                stmt.executeQuery("delete from creator where username = 'test_usr'");
            } catch(SQLException ignored) {}

            boolean insertResult = dao.insertUser("test_usr", "test_email", "test_psw");
            Assertions.assertTrue(insertResult);

            ResultSet rs = stmt.executeQuery("select * from creator where username = 'test_usr'");
            Assertions.assertTrue(rs.next());

            rs.close();
            stmt.close();
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }


    }

    @AfterAll
    void tearDown() {
        pool.releaseEveryConnection();
    }
}
