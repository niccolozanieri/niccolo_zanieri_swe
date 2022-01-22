package com.niccolo_zanieri_swe.dao;

import com.niccolo_zanieri_swe.model.Client;
import com.niccolo_zanieri_swe.model.Creator;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // TODO: finish correcting copied code
public class ClientDAOTest {
    private ConnectionPool pool;
    private ClientDAO dao;

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
        dao = new ClientDAO(pool);
    }

    @Test
    void insertClientTest() {
        Connection c = null;
        Statement stmt = null;

        try {
            c = pool.getConnection();
            stmt = c.createStatement();

            try {
                stmt.executeQuery("delete from client where username = 'test_usr'");
            } catch(SQLException ignored) {}

            dao.insertClient("test_usr", "test_email", "test_psw");

            ResultSet rs = stmt.executeQuery("select * from client where username = 'test_usr'");
            Assertions.assertTrue(rs.next());

            SQLException thrown = Assertions.assertThrows(
                    SQLException.class,
                    () -> {dao.insertClient("test_usr", "test_email", "test_psw");},
                    "Expected dao.insertClient(\"test_usr\", \"test_email\", \"test_psw\") to throw, but it didn't"
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
    void removeClientTest() {
        Connection c = null;
        Statement stmt = null;

        try {
            c = pool.getConnection();
            stmt = c.createStatement();

            try {
                stmt.executeQuery("insert into client values('test_usr', 'test_email', 'test_psw');");
            } catch(SQLException e) {
                System.err.println("");
            }

            dao.removeClient("test_usr");

            ResultSet rs = stmt.executeQuery("select * from client where username = 'test_usr'");
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

    //    should I put the following two tests in another class?
    @Test
    void followCreatorTest() {
        Connection c = null;
        Statement stmt = null;

        try {
            c = pool.getConnection();
            stmt = c.createStatement();

            CreatorDAO cr_dao = new CreatorDAO(pool);
            Creator followed = new Creator("test_usr1", "test_email1", "test_psw1");
            Client follower = new Client("test_usr2", "test_email2", "test_psw2");
            dao.insertClient(follower.getUsername(), follower.getEmail(), follower.getPassword());
            cr_dao.insertCreator(followed.getUsername(), followed.getEmail(), followed.getPassword());
            dao.followCreator(followed, follower);

            SQLException thrown = Assertions.assertThrows(
                    SQLException.class,
                    () -> {dao.followCreator(followed, follower);},
                    "Expected dao.followCreator(followed, follower) to throw, but it didn't"
            );

            String sql = "select * from followedByClient where followed_usr='test_usr1' and follower_usr='test_usr2'";
            ResultSet rs = stmt.executeQuery(sql);
            Assertions.assertTrue(rs.next());

            cr_dao.removeCreator(followed.getUsername());
            dao.removeClient(follower.getUsername());
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

            CreatorDAO cr_dao = new CreatorDAO(pool);
            Creator followed = new Creator("test_usr1", "test_email1", "test_psw1");
            Client follower = new Client("test_usr2", "test_email2", "test_psw2");
            dao.insertClient(follower.getUsername(), follower.getEmail(), follower.getPassword());
            cr_dao.insertCreator(followed.getUsername(), followed.getEmail(), followed.getPassword());
            dao.followCreator(followed, follower);
            dao.unfollowCreator(followed, follower);

            String sql = "select * from followedByCreator where followed_usr='test_usr1' and follower_usr='test_usr2'";
            ResultSet rs = stmt.executeQuery(sql);
            Assertions.assertFalse(rs.next());

            cr_dao.removeCreator(followed.getUsername());
            dao.removeClient(follower.getUsername());

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
