package com.niccolo_zanieri_swe.dao;

import com.niccolo_zanieri_swe.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatorDAO {
    public CreatorDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public boolean insertCreator(String usr, String email, String psw) throws SQLException {
        boolean result = false;
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new RuntimeException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "insert into creator values('" + usr + "', '" + email + "', '" + psw + "');";
            stmt.executeUpdate(sql);
            result = true;
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }

        return result;
    }

    public boolean removeCreator(String usr) throws SQLException  {
        boolean result = false;
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new RuntimeException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "delete from creator where username = '" + usr + "';";
            stmt.executeUpdate(sql);
            result = true;
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }

        return result;
    }

    public boolean followUser(User followed, User follower) throws SQLException {
        boolean result = false;
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new RuntimeException("There are no available connections.");
            }

            String followedUsr = followed.getUsername();
            String followerUsr = followed.getUsername();
            Statement stmt = c.createStatement();
            String sql = "insert into FollowedByCreator values('" + followedUsr +"', '" + followerUsr + "');";
            stmt.executeUpdate(sql);
            result = true;
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }

        return result;
    }

    private ConnectionPool pool;
}
