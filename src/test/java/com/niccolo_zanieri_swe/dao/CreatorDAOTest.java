package com.niccolo_zanieri_swe.dao;

import com.niccolo_zanieri_swe.model.Creator;
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
            pool = ConnectionPool.getConnectionPool(url, usr, psw);
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

            dao.insertCreator("test_usr", "test_email", "test_psw");

            ResultSet rs = stmt.executeQuery("select * from creator where username = 'test_usr'");
            Assertions.assertTrue(rs.next());

            SQLException thrown = Assertions.assertThrows(
                    SQLException.class,
                    () -> {dao.insertCreator("test_usr", "test_email", "test_psw");},
                    "Expected dao.insertCreator(\"test_usr\", \"test_email\", \"test_psw\"); to throw, but it didn't"
            );

            rs.close();
            stmt.close();
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }
    }

    @Test
    void removeCreatorTest() {
        Connection c = null;
        Statement stmt = null;

        try {
            c = pool.getConnection();
            stmt = c.createStatement();

            try {
                stmt.executeQuery("insert into creator values('test_usr', 'test_email', 'test_psw');");
            } catch(SQLException e) {
                System.err.println("");
            }

            dao.removeCreator("test_usr");

            ResultSet rs = stmt.executeQuery("select * from creator where username = 'test_usr'");
            Assertions.assertFalse(rs.next());

            rs.close();
            stmt.close();
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }
    }

//    should I put following two tests in another class?
    @Test
    void followCreatorTest() {
        Connection c = null;
        Statement stmt = null;

        try {
            c = pool.getConnection();
            stmt = c.createStatement();

            Creator followed = new Creator("test_usr1", "test_email1", "test_psw1");
            Creator follower = new Creator("test_usr2", "test_email2", "test_psw2");
            dao.insertCreator(followed.getUsername(), followed.getEmail(), followed.getPassword());
            dao.insertCreator(follower.getUsername(), follower.getEmail(), follower.getPassword());
            dao.followCreator(followed, follower);

            SQLException thrown = Assertions.assertThrows(
                    SQLException.class,
                    () -> {dao.followCreator(followed, follower);},
                    "Expected dao.followCreator(followed, follower) to throw, but it didn't"
            );

            String sql = "select * from followedByCreator where followed_usr='test_usr1' and follower_usr='test_usr2'";
            ResultSet rs = stmt.executeQuery(sql);
            Assertions.assertTrue(rs.next());

            dao.removeCreator(followed.getUsername());
            dao.removeCreator(follower.getUsername());
            rs = stmt.executeQuery(sql);
            Assertions.assertFalse(rs.next());

            stmt.close();
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }
    }

    @Test
    void unfollowUserTest() {
        Connection c = null;
        Statement stmt = null;

        try {
            c = pool.getConnection();
            stmt = c.createStatement();

            Creator followed = new Creator("test_usr1", "test_email1", "test_psw1");
            Creator follower = new Creator("test_usr2", "test_email2", "test_psw2");
            dao.insertCreator(followed.getUsername(), followed.getEmail(), followed.getPassword());
            dao.insertCreator(follower.getUsername(), follower.getEmail(), follower.getPassword());
            dao.followCreator(followed, follower);
            dao.unfollowCreator(followed, follower);

            String sql = "select * from followedByCreator where followed_usr='test_usr1' and follower_usr='test_usr2'";
            ResultSet rs = stmt.executeQuery(sql);
            Assertions.assertFalse(rs.next());

            dao.removeCreator(followed.getUsername());
            dao.removeCreator(follower.getUsername());

            stmt.close();
        } catch(SQLException e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
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
