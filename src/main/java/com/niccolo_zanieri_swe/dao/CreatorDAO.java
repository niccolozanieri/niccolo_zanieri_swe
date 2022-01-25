package com.niccolo_zanieri_swe.dao;

import com.niccolo_zanieri_swe.model.Creator;

import java.sql.Connection;
import java.sql.ResultSet;
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
                throw new SQLException("There are no available connections.");
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
                throw new SQLException("There are no available connections.");
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

    public void followCreator(Creator followed, Creator follower) throws SQLException {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new SQLException("There are no available connections.");
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

    public void unfollowCreator(Creator followed, Creator follower) throws SQLException {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new SQLException("There are no available connections.");
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

    public Creator findCreator(String usr) throws SQLException {
        Connection c = null;
        Creator creator = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new SQLException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "select * from creator where username='" + usr + "';";
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) {
                String psw = rs.getString("password");
                String email = rs.getString("email");
                creator = new Creator(usr, email, psw);
            }

            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }

        return creator;
    }

    private ConnectionPool pool;
}
