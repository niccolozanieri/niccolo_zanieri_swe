package com.niccolo_zanieri_swe.dao;

import com.niccolo_zanieri_swe.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatorDAO {
    public CreatorDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public void insertCreator(String usr, String email, String psw) throws SQLException {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new RuntimeException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "insert into creator values('" + usr + "', '" + email + "', '" + psw + "');";
            stmt.executeUpdate(sql);
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }
    }

    public void removeCreator(String usr) throws SQLException  {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new RuntimeException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "delete from creator where username = '" + usr + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }
    }

    public void followUser(User followed, User follower) throws SQLException {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new RuntimeException("There are no available connections.");
            }

            String followedUsr = followed.getUsername();
            String followerUsr = follower.getUsername();
            Statement stmt = c.createStatement();
            String sql = "insert into FollowedByCreator values('" + followedUsr +"', '" + followerUsr + "');";
            stmt.executeUpdate(sql);
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }

    }

    public void unfollowUser(User followed, User follower) throws SQLException {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new RuntimeException("There are no available connections.");
            }

            String followedUsr = followed.getUsername();
            String followerUsr = follower.getUsername();
            Statement stmt = c.createStatement();
            String sql = "delete from FollowedByCreator where followed_usr='" + followedUsr + "' and follower_usr='" + followerUsr +"';";
            stmt.executeUpdate(sql);
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }
    }

    private ConnectionPool pool;
}
