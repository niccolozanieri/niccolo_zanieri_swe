package com.niccolo_zanieri_swe.dao;

import com.niccolo_zanieri_swe.model.Client;
import com.niccolo_zanieri_swe.model.Creator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientDAO {
    public ClientDAO(ConnectionPool pool) {
        this.pool = pool;
    }

    public void insertClient(String usr, String email, String psw) throws SQLException {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new SQLException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "insert into client values('" + usr + "', '" + email + "', '" + psw + "');";
            stmt.executeUpdate(sql);
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }
    }

    public void removeClient(String usr) throws SQLException  {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new SQLException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "delete from client where username = '" + usr + "';";
            stmt.executeUpdate(sql);
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }
    }

    public void followCreator(Creator followed, Client follower) throws SQLException {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new SQLException("There are no available connections.");
            }

            String followedUsr = followed.getUsername();
            String followerUsr = follower.getUsername();
            Statement stmt = c.createStatement();
            String sql = "insert into FollowedByClient values('" + followedUsr +"', '" + followerUsr + "');";
            stmt.executeUpdate(sql);
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }

    }

    public void unfollowCreator(Creator followed, Client follower) throws SQLException {
        Connection c = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new SQLException("There are no available connections.");
            }

            String followedUsr = followed.getUsername();
            String followerUsr = follower.getUsername();
            Statement stmt = c.createStatement();
            String sql = "delete from FollowedByClient where followed_usr='" + followedUsr + "' and follower_usr='" + followerUsr +"';";
            stmt.executeUpdate(sql);
            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }
    }

    public Client findClient(String usr) throws SQLException {
        Connection c = null;
        Client client = null;
        try {
            c = pool.getConnection();
            if(c == null) {
                throw new SQLException("There are no available connections.");
            }

            Statement stmt = c.createStatement();
            String sql = "select * from client where username='" + usr + "';";
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) {
                String psw = rs.getString("password");
                String email = rs.getString("email");
                client = new Client(usr, email, psw);
            }

            stmt.close();
        } finally {
            if(c != null) {
                pool.releaseConnection(c);
            }
        }

        return client;
    }

    private ConnectionPool pool;
}
